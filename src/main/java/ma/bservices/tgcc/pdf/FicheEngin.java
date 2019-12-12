/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.Engin; 

/**
 *
 * @author yassine
 */
public class FicheEngin {

    public FicheEngin() {
    }
    	
	public String editeFicheEngin(String chemin,Engin e) {
             String cheminContrat="";
		try {
			//config
			//type d'ecriture
	                Font fontEntet = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                        Font fontcontent = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL); 
                        Font fontTitre = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD); 
                        Font fontP = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC); 
	        //variable de type date (valeur actuel)
			Date date = new Date();
			String dt = new SimpleDateFormat("dd/MM/yyyy").format(date);
			String dtNom = new SimpleDateFormat("dd-MM-yyyy-H-m-s").format(date);
                        String nomFichier = e.getCode()+dtNom;
                        String val="";
	        //saut de ligne sous forme paragraphe
			Paragraph vide = new Paragraph(" ");
			//tableau de deux clones
			PdfPTable table = new PdfPTable(2);
			PdfPTable tableImg = new PdfPTable(3);
			table.setWidthPercentage(100);
			//cellule vide sans bordure

			PdfPCell c1 = new PdfPCell(vide);
			PdfPCell c2 = new PdfPCell(vide);
                        
			PdfPCell c3 = new PdfPCell(vide);
			PdfPCell c4 = new PdfPCell(vide);
                        
			PdfPCell c5= new PdfPCell(vide);
			
			c1.setPadding(2); 
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(2);
			c2.setHorizontalAlignment(PdfPCell.RIGHT); 
			c2.setBorder(PdfPCell.NO_BORDER);

			c3.setPadding(2); 
			c3.setBorder(PdfPCell.NO_BORDER);

			c4.setPadding(2); 
			c4.setBorder(PdfPCell.NO_BORDER);

			c5.setPadding(2); 
			c5.setBorder(PdfPCell.NO_BORDER);
			
			//chemain de fichier générer
			String fileName =chemin+"/"+nomFichier+".pdf";
                        cheminContrat=fileName;
			System.out.println("chemin : "+fileName);
			//instance de document
			Document document = new Document();
			//creation d'un document de type paragraphe
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
						
			document.open();
                        //"src/main/java/resources/logo.png";
                        String imageFile = new File(ConstanteMb.getRepertoire()+"/SOURCES/logo.png").getAbsolutePath();
                                //"D:/logo.png";
                        Image img = Image.getInstance(imageFile);
                        c1 = new PdfPCell(img, true);
			c2 = new PdfPCell(vide); 
			c3 = new PdfPCell(new Paragraph("Date : "+dt,fontP)); 
                        
                        
			c1.setPadding(2);
			c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(2);
			c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			c3.setPadding(2);
			c3.setHorizontalAlignment(1);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			c2.setPadding(2);
			c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER); 
                        tableImg.addCell(c1);                        
                        tableImg.addCell(c2);
                        tableImg.addCell(c3);

                        document.add(tableImg);
                        document.add(vide);
                        
                        
			Paragraph code = new Paragraph("FICHE ENGIN \n CODE "+((e.getCode()!=null)?e.getCode():""),fontTitre);
                        code.setAlignment(1);
                        document.add(code);
                        document.add(vide);
                        
			c1 = new PdfPCell(new Paragraph("Designation ",fontEntet));
                        val=(e.getFamilleEngin()!=null)?e.getFamilleEngin():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
                        
			c3 =new PdfPCell(new Paragraph("Famille Engin ",fontEntet)); 
                        val=(e.getDesignation()!=null)?e.getDesignation():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        
			c1 = new PdfPCell(new Paragraph("Chantier ",fontEntet));
                        val=(e.getChantierLib()!=null)?e.getChantierLib():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Matricule ",fontEntet));
                        val=(e.getNum_immatriculation()!=null)?e.getNum_immatriculation():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        
			c1 = new PdfPCell(new Paragraph("Numéro chassis ",fontEntet));
                        val=(e.getNumchassis()!=null)?e.getNumchassis():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Etat ",fontEntet));
                        val=(e.getEtat()!=null)?e.getEtat():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet)); 
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2); 
			table.addCell(c3); 
			table.addCell(c4); 
                        
			c1 = new PdfPCell(new Paragraph("Marque ",fontEntet));
                        val=(e.getMarque()!=null)?e.getMarque():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Type Engin ",fontEntet));
                        val=(e.getTypeEngin()!=null)?e.getTypeEngin():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
			c1 = new PdfPCell(new Paragraph("Numéro série ",fontEntet));
                        val=(e.getNumSerie()!=null)?e.getNumSerie():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Type Pointage ",fontEntet));
                        val=(e.getActif()!=null)?((e.getActif().intValue()==1)?"Automatique":"Manuelle"):"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4);  
                       
                        //document.add(table);
                           
			c1 = new PdfPCell(new Paragraph("Type Compteur ",fontEntet));
                        val=(e.getTypeCompteur()!=null)?e.getTypeCompteur():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Conducteur ",fontEntet));
                        val=(e.getConducteur()!=null)?e.getConducteur().getMatricule()+"|"+e.getConducteur().getNom()+" "+e.getConducteur().getPrenom():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        //document.add(table);
                       
			c1 = new PdfPCell(new Paragraph("Compteur kilométrique ",fontEntet));
                        val=(e.getCompteurKilometrique()!=null)?e.getCompteurKilometrique().toString():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Compteur horaire ",fontEntet));
                        val=(e.getComteurHoraire()!=null)?e.getComteurHoraire().toString():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        
			c1 = new PdfPCell(new Paragraph("Fournisseur ",fontEntet));
                        val=(e.getFournisseur()!=null)?e.getFournisseur():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Année de construction ",fontEntet));
                        val=(e.getAnneeConst()!=null)?e.getAnneeConst():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4);  
                       
			c1 = new PdfPCell(new Paragraph("Date Aquisition ",fontEntet));
                        val=(e.getDateAquisition()!=null)? new SimpleDateFormat("dd/MM/yyyy").format(e.getDateAquisition()):"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Date de mise en service ",fontEntet));
                        val=(e.getDateMISESERVICE()!=null)? new SimpleDateFormat("dd/MM/yyyy").format(e.getDateMISESERVICE()):"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        //document.add(table);
                        
			c1 = new PdfPCell(new Paragraph("Date du dernier contrôle réglementaire",fontEntet));
                        val=(e.getDateDERVIS()!=null)?new SimpleDateFormat("dd/MM/yyyy").format(e.getDateDERVIS()):"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Date de le dernière vidange ",fontEntet));
                        val=(e.getDateDERVID()!=null)? new SimpleDateFormat("dd/MM/yyyy").format(e.getDateDERVID()):"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                        
                        
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        //document.add(table);
                        
                        //document.add(table);
                        
                        
                        
			c1 = new PdfPCell(new Paragraph("Prochaine vidange (heures) ",fontEntet));
                        val=(e.getNbrHeures()!=null)?e.getNbrHeures().toString():"";
			c2 = new PdfPCell(new Paragraph(val,fontEntet));
			c3 = new PdfPCell(new Paragraph("Prochaine vidange (km) ",fontEntet));
                        val=(e.getNbrKilometrage()!=null)?e.getNbrKilometrage().toString():"";
			c4 = new PdfPCell(new Paragraph(val,fontEntet));
                         
			c1.setPadding(5);
			c1.setHorizontalAlignment(0);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(5);
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER); 
                        
			c3.setPadding(5);
			c3.setHorizontalAlignment(0);
			//c3.setBorder(PdfPCell.NO_BORDER); 
                        
			c4.setPadding(5);
			c4.setHorizontalAlignment(0);
			//c4.setBorder(PdfPCell.NO_BORDER); 
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c4); 
                        
                        document.add(table);
			document.close();
			System.out.println("Fin d'éditions !");
			
                       // cheminContrat=fileName;
                        cheminContrat = cheminContrat.substring(cheminContrat.indexOf("files"));
		} catch (Exception ex) {
			System.out.println("Erreur d'édition l'affiche engin car "+ex.getMessage());
		}
                System.out.println("=================> "+cheminContrat);
            return cheminContrat;
		
	}


}
