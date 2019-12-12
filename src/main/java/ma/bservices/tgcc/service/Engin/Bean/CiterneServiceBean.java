/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin.Bean;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ma.bservices.beans.Chantier;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Engin;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.css.Rect;

/**
 *
 * @author a.wattah
 */
public class CiterneServiceBean implements Serializable {

    public CiterneServiceBean() {
    }

    /**
     * methode qui permet de compare list chantiers avec code chantier et
     * recupere liste des chantier sans code chantier passe au parametre
     *
     * @param l_chantier_p
     * @param chantier
     * @return
     */
    public List<Chantier> get_l_chantiers_sec(List<Chantier> l_chantier_p, String chantier) {

        if (l_chantier_p == null) {
            return l_chantier_p;
        }

        for (int j = 0; j < l_chantier_p.size(); j++) {

            if (l_chantier_p.get(j).getId().equals(Integer.valueOf(chantier))) {
                l_chantier_p.get(j).setDisplay_chantier_Principal(Boolean.FALSE);

            } else {
                l_chantier_p.get(j).setDisplay_chantier_Principal(Boolean.TRUE);
            }
        }

        return l_chantier_p;
    }

    public Integer getSomme_volume_actuel(Integer volume_ancien, Integer volume_actuel) {

        if (volume_ancien == null) {
            volume_ancien = 0;
        }

        if (volume_ancien != null) {

            return volume_ancien += volume_actuel;

        }
        return 0;

    }

    /**
     * methode qui calcule la soustraction de volume type citerne
     *
     * @param volume_ancien
     * @param volume_actuel
     * @return
     */
    public Double getSoustraction_volume_actuel(Double volume_ancien, Double volume_actuel) {

        Double volume = 0d;

        if (volume_actuel != null && volume_ancien != null) {
            volume = (Double)(volume_ancien - volume_actuel);
            return volume;
        }

        return volume;
    }

    /**
     * methode upload file
     *
     * @param uploadedFile
     */
    public String upload_fichier(UploadedFile uploadedFile) throws IOException {

        //String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document");
       String chemin = ConstanteMb.getRepertoire() + "/files/BL_CITERNE";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());

        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);

        try (InputStream input = uploadedFile.getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        }
        return chemin + "/" + file.getFileName();

    }

    /**
     * methode qui permet de telecharger fichier
     *
     * @param chemin
     * @throws java.io.FileNotFoundException
     */
    public void telecharger_fichier(String chemin) throws FileNotFoundException, IOException {

        if (chemin != null && !"".equals(chemin)) {

            FacesContext context = FacesContext.getCurrentInstance();

            File file = new File(chemin);

            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + file.getName() + "\"");
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(file),
                        DEFAULT_BUFFER_SIZE);
                output = new BufferedOutputStream(response.getOutputStream(),
                        DEFAULT_BUFFER_SIZE);
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } finally {
                if (input != null) {
                    input.close();
                    output.close();
                }
            }
            context.responseComplete();
        }

    }

    /**
     * methode pour ajouter dans une liste
     */
    public List<Engin> get_list_engin_chantier(List<Engin> l_engins1, List<Engin> l_engins2) {

        List<Engin> l_engins = new ArrayList<>();

        if (l_engins1 != null) {

            for (int i = 0; i < l_engins1.size(); i++) {

                l_engins.add(l_engins1.get(i));

            }
        }

        if (l_engins2 != null) {

            for (int i = 0; i < l_engins2.size(); i++) {

                if (l_engins.size() > 0) {
                    if (l_engins.contains(l_engins2.get(i))) {
                        l_engins.remove(l_engins2.get(i));
                    }
                }

                l_engins.add(l_engins2.get(i));

            }
        }

        return l_engins;
    }

    public static PdfPTable createFirstTable() {
        // a table with three columns
        PdfPTable table = new PdfPTable(4);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cell with colspan 2"));
        cell.setColspan(2);

        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with colspan 22"));
        cell.setColspan(2);

        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cell with colspan 23"));
        cell.setColspan(2);

        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cell with colspan 22"));
        cell.setColspan(2);

        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cell with colspan 23"));
        cell.setColspan(2);

        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cell with colspan 24"));
        cell.setColspan(2);

        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    public Paragraph makePara(String p1, String p2) {
        Paragraph p = new Paragraph();
        Font ff = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
        Font fb = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        p.add(new Phrase(p1, ff));
        p.add(new Phrase(p2, fb));
        return p;
    }

    /**
     * methode pour telecharger fichier bon caisse
     *
     * @param bon_Livraison_Citerne
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public String telecharger_bon_gasoil_engin(Bon_Livraison_Citerne bon_Livraison_Citerne) throws IOException, DocumentException {

        if (bon_Livraison_Citerne != null) {

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document");
            String chemin = ConstanteMb.getRepertoire() + "/files/BG_Engin";
            String nomFichier = "BonGasoilEngin "+ bon_Livraison_Citerne.getId() +" .pdf";
            File file = new File(chemin + nomFichier);
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
            Image image1 = Image.getInstance(chemin_Image);

            document.add(image1);
            Font f = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.BOLD, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Bon De Gasoil ", f);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            String date_string = this.convertDate_To_string(bon_Livraison_Citerne.getDate());

            PdfPTable tx = new PdfPTable(2);
            tx.setWidthPercentage(100);

            PdfPCell cellFive = new PdfPCell(makePara("Date : ", date_string));
            PdfPCell cellTree = new PdfPCell(makePara("N° Bon : ", bon_Livraison_Citerne.getId().toString()));

            cellTree.setBorder(Rectangle.NO_BORDER);
            cellFive.setBorder(Rectangle.NO_BORDER);

            tx.addCell(cellFive);
            tx.addCell(cellTree);

            tx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tx.getDefaultCell().setFixedHeight(100);
            document.add(tx);

            document.add(new Paragraph(" "));

            PdfPTable txx = new PdfPTable(2);
            txx.setWidthPercentage(100);
            PdfPCell cellFivex = new PdfPCell(makePara("Chantier : ", bon_Livraison_Citerne.getCiterne().getChantier_Principal().getCode()));
            PdfPCell cellTreex = new PdfPCell(makePara("Citerne : ", bon_Livraison_Citerne.getCiterne().getLibelle_citerne()));

            cellTreex.setBorder(Rectangle.NO_BORDER);
            cellFivex.setBorder(Rectangle.NO_BORDER);

            txx.addCell(cellFivex);
            txx.addCell(cellTreex);

            txx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            txx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            txx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            txx.getDefaultCell().setFixedHeight(100);
            document.add(txx);

            document.add(new Paragraph(" "));

            PdfPTable txxx = new PdfPTable(2);
            txxx.setWidthPercentage(100);
            PdfPCell cellFivexx = new PdfPCell(makePara("Chantier Engin : ", bon_Livraison_Citerne.getEngin().getPrjapId().getCode()));
            PdfPCell cellTreexx = new PdfPCell(makePara("Engin : ", bon_Livraison_Citerne.getEngin().getCode() + "- " + bon_Livraison_Citerne.getEngin().getDesignation()));

            cellTreexx.setBorder(Rectangle.NO_BORDER);
            cellFivexx.setBorder(Rectangle.NO_BORDER);

            txxx.addCell(cellFivexx);
            txxx.addCell(cellTreexx);

            txxx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            txxx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            txxx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            txxx.getDefaultCell().setFixedHeight(70);
            document.add(txxx);

            document.add(new Paragraph(" "));

            PdfPTable t4s = new PdfPTable(2);
            t4s.setWidthPercentage(100);

            PdfPCell cellFives = new PdfPCell(makePara("Kilométrage : ", bon_Livraison_Citerne.getKilometrage() + "KM"));
            PdfPCell cellTrees = new PdfPCell(makePara("Compteur Horraire : ", bon_Livraison_Citerne.getHeure() + " H "));

            cellTrees.setBorder(Rectangle.NO_BORDER);
            cellFives.setBorder(Rectangle.NO_BORDER);

            t4s.addCell(cellFives);
            t4s.addCell(cellTrees);

            t4s.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            t4s.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            t4s.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            t4s.getDefaultCell().setFixedHeight(70);
            document.add(t4s);

            document.add(new Paragraph(" "));

            document.add(makePara(" Quantité : ", bon_Livraison_Citerne.getVolume() + " L"));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            PdfPTable signs = new PdfPTable(3);
            signs.setWidthPercentage(100);
            PdfPCell eme = new PdfPCell(new Phrase("Nom et Signature du pompiste: "));
            PdfPCell chauf = new PdfPCell(new Phrase("Nom et Signature du Pointeur: "));
            PdfPCell rec = new PdfPCell(new Phrase("Nom et Signature Conducteur/ Chef d'équipe: "));

            signs.addCell(eme);
            signs.addCell(chauf);
            signs.addCell(rec);

            PdfPCell eme_empty = new PdfPCell(new Phrase(""));
            PdfPCell chauf_empty = new PdfPCell(new Phrase(" "));
            PdfPCell rec_empty = new PdfPCell(new Phrase(" "));

            eme_empty.setFixedHeight(80);
            chauf_empty.setFixedHeight(80);
            rec_empty.setFixedHeight(80);

            signs.addCell(eme_empty);
            signs.addCell(chauf_empty);
            signs.addCell(rec_empty);

            signs.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            signs.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            signs.getDefaultCell().setFixedHeight(100);

            document.add(signs);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
//            document.add(new Paragraph("Signature  récépteur :"));
//            Paragraph paragraph_emetteur = new Paragraph("Signature de l'émetteur :");
//            paragraph_emetteur.setAlignment(Element.ALIGN_RIGHT);
//            document.add(paragraph_emetteur);
            document.close();
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + nomFichier + "\"");
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(file),
                        DEFAULT_BUFFER_SIZE);
                output = new BufferedOutputStream(response.getOutputStream(),
                        DEFAULT_BUFFER_SIZE);
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } finally {
                input.close();
                output.close();
            }
            context.responseComplete();

            bon_Livraison_Citerne.setChemin_fichier(chemin + nomFichier);
            System.out.println("CHEMIN BG ENGIN SET TO : " + bon_Livraison_Citerne);

            return chemin + nomFichier;
        }

        return "";
    }

    /**
     * methode qui permet de converti date to string
     *
     * @param date
     * @return
     */
    private String convertDate_To_string(Date date) {

        String date_String = "";

        if (date != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            date_String = sdf.format(date);
        }

        return date_String;

    }

    /**
     * methode qui permet de telecharger bon gasoil mensuel
     */
    /**
     * methode qui permet de telecharger bon gasoil mensuel
     *
     * @param bon_Livraison_Citerne
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public void telecharger_bon_gasoil_mensuel(Bon_Livraison_Citerne bon_Livraison_Citerne) throws IOException, DocumentException {

        if (bon_Livraison_Citerne != null) {

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document");
            String chemin = ConstanteMb.getRepertoire() + "/files/BG_Mensuel";
            String nomFichier = "BonGasoilMensuel "+bon_Livraison_Citerne.getId() + ".pdf";
            File file = new File(chemin + nomFichier);
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
            Image image1 = Image.getInstance(chemin_Image);

            document.add(image1);
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.BOLD, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Bon De Gasoil ", f);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            String date_string = this.convertDate_To_string(bon_Livraison_Citerne.getDate());

            /**
             * NEEEEEEEEEEEEEEEWWWWWWWW BGM *
             */
            PdfPTable tx = new PdfPTable(2);
            tx.setWidthPercentage(100);

            PdfPCell cellFive = new PdfPCell(makePara("Date  : ", date_string));
            PdfPCell cellTree = new PdfPCell(makePara("N° Bon : ", bon_Livraison_Citerne.getId().toString()));

            cellTree.setBorder(Rectangle.NO_BORDER);
            cellFive.setBorder(Rectangle.NO_BORDER);

            tx.addCell(cellFive);
            tx.addCell(cellTree);

            tx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tx.getDefaultCell().setFixedHeight(100);
            document.add(tx);

            document.add(new Paragraph(" "));

            PdfPTable txx = new PdfPTable(2);
            txx.setWidthPercentage(100);
            PdfPCell cellFivex = new PdfPCell(makePara("Chantier : ", bon_Livraison_Citerne.getCiterne().getChantier_Principal().getCode()));
            PdfPCell cellTreex = new PdfPCell(makePara("Citerne : ", bon_Livraison_Citerne.getCiterne().getLibelle_citerne()));

            cellTreex.setBorder(Rectangle.NO_BORDER);
            cellFivex.setBorder(Rectangle.NO_BORDER);

            txx.addCell(cellFivex);
            txx.addCell(cellTreex);

            txx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            txx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            txx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            txx.getDefaultCell().setFixedHeight(100);
            document.add(txx);

            document.add(new Paragraph(" "));
            document.add(makePara(" Bénéficiaire : ", bon_Livraison_Citerne.getMensuel().getNom() + " " + bon_Livraison_Citerne.getMensuel().getPrenom()));
            document.add(new Paragraph(" "));
            document.add(makePara(" Quantité : ", bon_Livraison_Citerne.getVolume() + " L"));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            PdfPTable signs = new PdfPTable(3);
            signs.setWidthPercentage(100);
            PdfPCell eme = new PdfPCell(new Phrase("Nom et Signature du pompiste : "));
            PdfPCell chauf = new PdfPCell(new Phrase("Nom et Signature du Pointeur : "));
            PdfPCell rec = new PdfPCell(new Phrase("Nom et Signature Bénéficiaire : "));

            signs.addCell(eme);
            signs.addCell(chauf);
            signs.addCell(rec);

            PdfPCell eme_empty = new PdfPCell(new Phrase(""));
            PdfPCell chauf_empty = new PdfPCell(new Phrase(" "));
            PdfPCell rec_empty = new PdfPCell(new Phrase(" "));

            eme_empty.setFixedHeight(80);
            chauf_empty.setFixedHeight(80);
            rec_empty.setFixedHeight(80);

            signs.addCell(eme_empty);
            signs.addCell(chauf_empty);
            signs.addCell(rec_empty);

            signs.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            signs.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            signs.getDefaultCell().setFixedHeight(100);

            document.add(signs);

            /* END OF NEEEEWWWWWW */
            document.add(new Paragraph(" "));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.close();
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + nomFichier + "\"");
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(file),
                        DEFAULT_BUFFER_SIZE);
                output = new BufferedOutputStream(response.getOutputStream(),
                        DEFAULT_BUFFER_SIZE);
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } finally {
                input.close();
                output.close();
            }
            context.responseComplete();

            bon_Livraison_Citerne.setChemin_fichier(chemin + nomFichier);
        }

    }

    /**
     * methode qui permet de supprimer donne dans list chantier_sec
     *
     * @param l_chnatier1
     * @param l_chantier2
     * @return
     */
    public List<Chantier> merge_to_listChantier_display(List<Chantier> l_chnatier1, List<Chantier> l_chantier2) {

        if (l_chantier2 != null && l_chnatier1 != null) {

            for (int i = 0; i < l_chantier2.size(); i++) {
                if (l_chnatier1.contains(l_chantier2.get(i))) {

                    l_chantier2.get(i).setDisplay_chantier_Principal(Boolean.FALSE);

                    System.out.println("entre 3 : " + l_chantier2.get(i).getDisplay_chantier_Principal());

                } else {

                    l_chantier2.get(i).setDisplay_chantier_Principal(Boolean.TRUE);
                    System.out.println("entre 4 : " + l_chantier2.get(i).getDisplay_chantier_Principal());
                }
            }
            return l_chantier2;
        }
        return null;
    }

}
