/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.ConstanteMb;

/**
 *
 * @author yassine
 */
public class Badge {

    public Badge() {
    }
    
	public String editeBadge(String chemin,ArrayList<Salarie> salaries) {
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
                        String nomFichier = "Badges_Salaries_"+dtNom;
                        String val="";
	        //saut de ligne sous forme paragraphe
			Paragraph vide = new Paragraph(" ");
			//tableau de deux clones
			PdfPTable tableImg = new PdfPTable(3);
			//cellule vide sans bordure
                        
			PdfPCell c1 = new PdfPCell(vide);
			PdfPCell c2 = new PdfPCell(vide);
			PdfPCell c3 = new PdfPCell(vide); 
                        
			c1.setPadding(2); 
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(2);
			c2.setHorizontalAlignment(PdfPCell.RIGHT); 
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			c3.setPadding(2); 
			c3.setBorder(PdfPCell.NO_BORDER);
                         
			
			//chemain de fichier générer
			String fileName =chemin+"/"+nomFichier+".pdf";
                        cheminContrat=fileName;
			System.out.println("chemin : "+fileName);
			//instance de document
			Document document = new Document();
			//creation d'un document de type paragraphe
			 PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
						
			document.open();
                        //"src/main/java/resources/logo.png"; 
                        int i = 0;
                 for (Salarie s : salaries){
                            System.out.println("Salaries : "+s.toString());
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
                        String imageFile = new File(ConstanteMb.getRepertoire()+"/SOURCES/logo.png").getAbsolutePath();
                                //"D:/logo.png";
                        Image img = Image.getInstance(imageFile);
                        img.setWidthPercentage(50);
                        c1 = new PdfPCell(img, true);
			c2 = new PdfPCell(new Paragraph("\n PHOTO",fontEntet)); 
			c3 = new PdfPCell(vide);    
                        
                        
			c1.setPadding(2);
			c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(2);
			c2.setHorizontalAlignment(2);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			c3.setPadding(2);
			c3.setHorizontalAlignment(1);
			c3.setBorder(PdfPCell.NO_BORDER); 
                         
                        table.addCell(c1);                        
                        table.addCell(c2);
                        table.addCell(c3);
                        table.addCell(c3);
                        table.addCell(c3);

			Paragraph txt = new Paragraph(((s.getNom() !=null)?s.getNom():"")+"\n"
                                +((s.getPrenom()!=null)?s.getPrenom():"")+"\n"
                                +((s.getFonction() !=null)?s.getFonction().getFonction():"")+"\n"
                                +((s.getCin()!=null)?s.getCin():"")+"\n",fontcontent);
                        txt.setAlignment(0); 
			c1 = new PdfPCell(txt);
			c2 = new PdfPCell(vide);
                        
                        
                        
			c1.setPadding(2);
			c1.setBorder(PdfPCell.NO_BORDER); 
			c1.setHorizontalAlignment(0);
                        c1.setColspan(2);
			//c1.setBorder(PdfPCell.NO_BORDER); 
                        
			c2.setPadding(0);
			c2.setBorder(PdfPCell.NO_BORDER); 
			c2.setHorizontalAlignment(0);
			//c2.setBorder(PdfPCell.NO_BORDER);   
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
			table.addCell(c3); 
                        
                         
                         
                        PdfContentByte cb = writer.getDirectContent(); 
                        Barcode128 barcode128 = new Barcode128();
                        barcode128.setCode(s.getMatricule());
                        barcode128.setCodeType(Barcode.CODE128);
                        Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
                        code128Image.setWidthPercentage(80);
                        c1 = new PdfPCell(code128Image, true); 

                        c1.setFixedHeight(50f);
                        c1.setPadding(2); 
                        c1.setBorder(PdfPCell.NO_BORDER); 
                        c1.setColspan(3);

                            //c1.setBorder(PdfPCell.NO_BORDER); 
   
                        table.addCell(c1); 
			table.addCell(c3); 
			table.addCell(c3);
                        
                        document.add(table);
                        if(i%5==0 && i>1){
                           document.add(vide);
                           document.add(vide); 
                           i=0;
                        }else{
                            i++;
                        }
                    }

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
public  PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {

        BarcodeEAN barcode = new BarcodeEAN();

        barcode.setCodeType(Barcode.CODE128);

        barcode.setCode(code);

        PdfPCell cell = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);

        cell.setPadding(10);

        return cell;

    }
}
