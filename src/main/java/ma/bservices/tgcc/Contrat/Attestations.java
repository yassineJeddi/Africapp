/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Contrat;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter; 
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; 
import java.util.Locale; 
import javax.swing.ImageIcon;
import ma.bservices.beans.Salarie;
import java.awt.*;

/**
 *
 * @author yassine
 */
public class Attestations {
  /*  public static void main(String[] args) {
            Date d = new Date();
            
                attestationTravail("D://", "Attestation travaille"+d.getTime(), "Casablanca", "yassine JEDDI", "Ingénieur SI", "2132456789", d);
                certificatTravail( "D://", "Certificat travaille" +d.getTime(), "Casablanca", "yassine JEDDI", "Ingénieur SI", "2132456789", d, d);
                attestationSalaire("D://", "Attestation de salaire"+d.getTime(), "Casablanca", "yassine JEDDI", "Ingénieur SI", "2132456789", d, "30000.5");
           
            domiciliationSalaire("D://", "domiciliationSalaire", "Banque Populaire", "TGCC - CASABLANCA", "123456789", "Yassine JEDDI");
} */

    public  String attestationTravail(String chemin, String nomFichier,String Ville,String nom,String fonction,String cnss ,Date dateDebut) {
            String cheminFichier="";
            try {
                chemin=(chemin!=null)?chemin:"";
                nomFichier=(nomFichier!=null)?nomFichier:"";
                Ville=(Ville!=null)?Ville:"";
                nom=(nom!=null)?nom:"";
                fonction=(fonction!=null)?fonction:"";
                cnss=(cnss!=null)?cnss:"";
                dateDebut=(dateDebut!=null)?dateDebut:new Date();               
                
                
                
			//config
			//type d'ecriture
	        Font fontEntet = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	        Font fonttitre = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	        Font fontcontent = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); 
	        Font fontP = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Chunk titreUnderline = new Chunk("");
                
                
                Paragraph ent       = new Paragraph("",fontEntet);
                Paragraph titreP    = new Paragraph("",fontEntet);
                Paragraph contentP  = new Paragraph("",fontEntet);
                
		PdfPTable table = new PdfPTable(2);
                PdfPCell c1 = new PdfPCell(titreP);
                PdfPCell c2 = new PdfPCell(titreP);
                PdfPCell c3 = new PdfPCell(titreP);
                
	        //variable de type date (valeur actuel)
			Date date = new Date();
			String dt = new SimpleDateFormat("dd/MM/yyyy").format(date);
	        //saut de ligne sous forme paragraphe
			Paragraph vide = new Paragraph(" ");
                //LOGO 
                         
                        
                //Fin LOGO
                //chemain de fichier générer
			String fileName =chemin+nomFichier+".pdf";
			System.out.println("chemin : "+fileName);
                //instance de document
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                //creation d'un document de type paragraphe
                        PdfWriter writer ;
			writer=PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open(); 
                        
                        titreP= new Paragraph("Casablanca, le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(date)),fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(1);
                      
                        c1 = new PdfPCell(new Paragraph("ATTESTATION DE TRAVAIL",fonttitre));
                         
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(1);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			table.addCell(c1);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        Paragraph p = new Paragraph("Nous soussignés, Entreprise BENELHOU FRERES SARL,"
                                + " Entrepreneur de Bâtiments, sise 8, Lotissement Yassmine 1, Boulevard Al Joulane,"
                                + " Sidi Othmane - Casablanca - Maroc, attestons par la présente que :",fontP);
                        
			document.add(p); 
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(3);
                      
                        c1 = new PdfPCell(new Paragraph("MONSIEUR",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+nom,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2); 
                        
                         
                      
                        c1 = new PdfPCell(new Paragraph("FONCTION",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+fonction,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);  
                         
                      
                        c1 = new PdfPCell(new Paragraph("C.N.S.S",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+cnss,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2); 
                        
			document.add(table);
                        
			document.add(vide);  
			document.add(vide);   
                         
                         p = new Paragraph("Est employé(e) au sein de notre société depuis le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(dateDebut)),fontP);
                        
			document.add(p); 
			document.add(vide);   
                         
                         p = new Paragraph("En foi de quoi, la présente attestation lui est délivrée pour servir et valoir ce Que de droit.",fontP);
                        
			document.add(p); 
			document.add(vide);
                        
                        titreP= new Paragraph("Direction ",fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
                        table = new PdfPTable(2);
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
			document.close();
			System.out.println("Fin d'éditions !");
			
                        cheminFichier=fileName;
                        cheminFichier=cheminFichier.substring(cheminFichier.indexOf("files"));
		} catch (Exception e) {
			System.out.println("Erreur d'édition de la facture car "+e.getMessage());
		}
		return cheminFichier;
	}
    public  String certificatTravail(String chemin, String nomFichier,String Ville,String nom,String fonction,String cnss ,Date dateDebut,Date dateFin) {
            String cheminFichier="";
            try {
                
                chemin=(chemin!=null)?chemin:"";
                nomFichier=(nomFichier!=null)?nomFichier:"";
                Ville=(Ville!=null)?Ville:"";
                nom=(nom!=null)?nom:"";
                fonction=(fonction!=null)?fonction:"";
                cnss=(cnss!=null)?cnss:"";
                dateDebut=(dateDebut!=null)?dateDebut:new Date();  
                dateFin=(dateFin!=null)?dateFin:new Date();  
                
			//config
			//type d'ecriture
	        Font fontEntet = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	        Font fonttitre = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	        Font fontcontent = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); 
	        Font fontP = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Chunk titreUnderline = new Chunk("");
                
                
                Paragraph ent       = new Paragraph("",fontEntet);
                Paragraph titreP    = new Paragraph("",fontEntet);
                Paragraph contentP  = new Paragraph("",fontEntet);
                
		PdfPTable table = new PdfPTable(2);
                PdfPCell c1 = new PdfPCell(titreP);
                PdfPCell c2 = new PdfPCell(titreP);
                PdfPCell c3 = new PdfPCell(titreP);
                
	        //variable de type date (valeur actuel)
			Date date = new Date();
			String dt = new SimpleDateFormat("dd/MM/yyyy").format(date);
	        //saut de ligne sous forme paragraphe
			Paragraph vide = new Paragraph(" ");
                //LOGO 
                         
                        
                //Fin LOGO
                //chemain de fichier générer
			String fileName =chemin+nomFichier+".pdf";
			System.out.println("chemin : "+fileName);
                //instance de document
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                //creation d'un document de type paragraphe
                        PdfWriter writer ;
			writer=PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open(); 
                        
                        titreP= new Paragraph("Casablanca, le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(date)),fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(1);
                      
                        c1 = new PdfPCell(new Paragraph("CERTIFICAT DE TRAVAIL",fonttitre));
                         
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(1);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			table.addCell(c1);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        Paragraph p = new Paragraph("Nous soussignés, Entreprise BENELHOU FRERES SARL,"
                                + " Entrepreneur de Bâtiments, sise 8, Lotissement Yassmine 1, Boulevard Al Joulane,"
                                + " Sidi Othmane - Casablanca - Maroc, attestons par la présente que :",fontP);
                        
			document.add(p); 
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(3);
                      
                        c1 = new PdfPCell(new Paragraph("MONSIEUR",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+nom,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2); 
                        
                        
                      
                        c1 = new PdfPCell(new Paragraph("FONCTION",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+fonction,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);
                        
                        
                      
                        c1 = new PdfPCell(new Paragraph("C.N.S.S",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+cnss,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2); 
                        
			document.add(table);
                        
			document.add(vide);   
                         
                         p = new Paragraph("Est employé(e) au sein de notre société depuis le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(dateDebut)),fontP);
                        
			document.add(p); 
			document.add(vide);   
                         
                         p = new Paragraph("Il nous a quitté libre de tout engagement après avoir perçu toutes ses indemnités le"+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(dateFin)),fontP);
                        
			document.add(p); 
			document.add(vide);   
                         
                         p = new Paragraph("En foi de quoi, la présente attestation lui est délivrée pour servir et valoir ce Que de droit.",fontP);
                        
			document.add(p); 
			document.add(vide);
                        
                        titreP= new Paragraph("Direction ",fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
                        table = new PdfPTable(2);
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
			document.close();
			System.out.println("Fin d'éditions !");
			
                        
                        cheminFichier=fileName;
                        cheminFichier=cheminFichier.substring(cheminFichier.indexOf("files"));
		} catch (Exception e) {
			System.out.println("Erreur d'édition de la facture car "+e.getMessage());
		}
		return cheminFichier;
	}
    public  String attestationSalaire(String chemin, String nomFichier,String Ville,String nom,String fonction,String cnss ,Date dateDebut,String salaire) {
	String cheminFichier="";	
        try {
            
                
                chemin=(chemin!=null)?chemin:"";
                nomFichier=(nomFichier!=null)?nomFichier:"";
                Ville=(Ville!=null)?Ville:"";
                nom=(nom!=null)?nom:"";
                fonction=(fonction!=null)?fonction:"";
                cnss=(cnss!=null)?cnss:"";
                dateDebut=(dateDebut!=null)?dateDebut:new Date(); 
                salaire=(salaire!=null)?salaire:""; 
                
			//config
			//type d'ecriture
	        Font fontEntet = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	        Font fonttitre = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	        Font fontcontent = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); 
	        Font fontP = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Chunk titreUnderline = new Chunk("");
                
                
                Paragraph ent       = new Paragraph("",fontEntet);
                Paragraph titreP    = new Paragraph("",fontEntet);
                Paragraph contentP  = new Paragraph("",fontEntet);
                
		PdfPTable table = new PdfPTable(2);
                PdfPCell c1 = new PdfPCell(titreP);
                PdfPCell c2 = new PdfPCell(titreP);
                PdfPCell c3 = new PdfPCell(titreP);
                
	        //variable de type date (valeur actuel)
			Date date = new Date();
			String dt = new SimpleDateFormat("dd/MM/yyyy").format(date);
	        //saut de ligne sous forme paragraphe
			Paragraph vide = new Paragraph(" ");
                //LOGO 
                         
                        
                //Fin LOGO
                //chemain de fichier générer
			String fileName =chemin+nomFichier+".pdf";
			System.out.println("chemin : "+fileName);
                //instance de document
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                //creation d'un document de type paragraphe
                        PdfWriter writer ;
			writer=PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open(); 
                        
                        titreP= new Paragraph("Casablanca, le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(date)),fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(1);
                      
                        c1 = new PdfPCell(new Paragraph("ATTESTATION DE SALAIRE",fonttitre));
                         
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(1);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			table.addCell(c1);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        Paragraph p = new Paragraph("Nous soussignés, Entreprise BENELHOU FRERES SARL,"
                                + " Entrepreneur de Bâtiments, sise 8, Lotissement Yassmine 1, Boulevard Al Joulane,"
                                + " Sidi Othmane - Casablanca - Maroc, attestons par la présente que :",fontP);
                        
			document.add(p); 
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(3);
                      
                        c1 = new PdfPCell(new Paragraph("MONSIEUR",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+nom,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);  
                        
                        
                      
                        c1 = new PdfPCell(new Paragraph("FONCTION",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+fonction,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2); 
                        
                        
                      
                        c1 = new PdfPCell(new Paragraph("C.N.S.S",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+cnss,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        c2.setColspan(2);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);
                         
                      
                        c1 = new PdfPCell(new Paragraph("SALAIRE NET ",fontEntet));
                        c2 = new PdfPCell(new Paragraph(":  "+salaire,fontcontent));
                        c3 = new PdfPCell(new Paragraph("",fontEntet));
                        
			c1.setPadding(5); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(5);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
			c3.setPadding(5);
                        c3.setHorizontalAlignment(0);
			c3.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c3); 
                        
			document.add(table);
                        
			document.add(vide);   
                         
                         p = new Paragraph("Est employé(e) au sein de notre société depuis le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(dateDebut)),fontP);
                        
			document.add(p); 
			document.add(vide);   
                          
                         
                         p = new Paragraph("En foi de quoi, la présente attestation lui est délivrée pour servir et valoir ce Que de droit.",fontP);
                        
			document.add(p); 
			document.add(vide);
                        
                        titreP= new Paragraph("Direction ",fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        
                        table = new PdfPTable(2);
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
			document.close();
			System.out.println("Fin d'éditions !");
			
                        cheminFichier=fileName;
                        cheminFichier=cheminFichier.substring(cheminFichier.indexOf("files"));
		} catch (Exception e) {
			System.out.println("Erreur d'édition de la facture car "+e.getMessage());
		}
		return cheminFichier;
	}
    public  String domiciliationSalaire(String chemin, String nomFichier,String banque,String agence,String numCpt,String nomPrenom ) {
	String cheminFichier="";	
        try {
            
                
                chemin=(chemin!=null)?chemin:"";
                nomFichier=(nomFichier!=null)?nomFichier:"";
                banque=(banque!=null)?banque:"";
                agence=(agence!=null)?agence:"";
                nomPrenom=(nomPrenom!=null)?nomPrenom:"";
                numCpt=(numCpt!=null)?numCpt:"";
                
			//config
			//type d'ecriture
	        Font fontEntet = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	        Font fonttitre = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	        Font fontcontent = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); 
	        Font fontP = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Chunk titreUnderline = new Chunk("");
                
                
                Paragraph ent       = new Paragraph("",fontEntet);
                Paragraph titreP    = new Paragraph("",fontEntet);
                Paragraph contentP  = new Paragraph("",fontEntet);
                
		PdfPTable table = new PdfPTable(2);
                PdfPCell c1 = new PdfPCell(titreP);
                PdfPCell c2 = new PdfPCell(titreP);
                PdfPCell c3 = new PdfPCell(titreP);
                
	        //variable de type date (valeur actuel)
			Date date = new Date();
			String dt = new SimpleDateFormat("dd/MM/yyyy").format(date);
	        //saut de ligne sous forme paragraphe
			Paragraph vide = new Paragraph(" ");
                //LOGO 
                         
                        
                //Fin LOGO
                //chemain de fichier générer
			String fileName =chemin+nomFichier+".pdf";
			System.out.println("chemin : "+fileName);
                //instance de document
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                //creation d'un document de type paragraphe
                        PdfWriter writer ;
			writer=PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open(); 
                        
                        
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        table = new PdfPTable(1);
                      
                        c1 = new PdfPCell(new Paragraph("ENGAGEMENT IRREVOCABLE DE DOMICILIATION DE SALAIRE",fonttitre));
                         
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(1);
			c1.setBorder(PdfPCell.NO_BORDER);
                        
			table.addCell(c1);
                        
			document.add(table);
                        
			document.add(vide);
			document.add(vide);
			document.add(vide);
                        
                        Paragraph p = new Paragraph("         Nous soussignés, Entreprise BENELHOU FRERES SARL, sise 8,"
                                + " Lotissement Yassmine 1, Boulevard Al Joulane, Sidi Othmane, Casablanca - Maroc,"
                                + " nous nous engageons à virer irrévocablement le salaire de Mr."+nomPrenom
                                + " à son compte ouvert à la "+banque+", agence "+agence
                                + ", sous le n° "+numCpt+", et ce, jusqu’à nouvel ordre de cette banque.",fontP);
                        
			document.add(p); 
			document.add(vide);
			document.add(vide);
                        
                        p = new Paragraph("         La "+banque+" devra obligatoirement être informée par écrit en"
                                + " cas de démission ou licenciement de l’intéressé. Et dans ce cas,"
                                + " la liquidation définitive de son compte à la société devra passer exclusivement"
                                + " par le compte bancaire précité.",fontP);
                        
			document.add(p); 
			document.add(vide);
			document.add(vide);
                        
                        titreP= new Paragraph("Casablanca, le "+(new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE).format(date)),fontEntet);
                        c1 = new PdfPCell(new Paragraph("",fontEntet));
                        c2 = new PdfPCell(titreP);
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
			c1.setBorder(PdfPCell.NO_BORDER);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(0);
			c2.setBorder(PdfPCell.NO_BORDER);
                        table = new PdfPTable(2);
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
			document.close();
			System.out.println("Fin d'éditions !");
			
                        cheminFichier=fileName;
                        cheminFichier=cheminFichier.substring(cheminFichier.indexOf("files"));
		} catch (Exception e) {
			System.out.println("Erreur d'édition de la facture car "+e.getMessage());
		}
		return cheminFichier;
	}
    public String editeBadge(String chemin,ArrayList<Salarie> salaries,String nomFile) {
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
                        String nomFichier = nomFile+dtNom;
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
                        String imageFile = new File("/opt/files/logo.png").getAbsolutePath();
                                //"D:/logo.png";
                        Image img = Image.getInstance(imageFile);  
                        
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
                        cheminContrat=fileName;
                        cheminContrat = cheminContrat.substring(cheminContrat.indexOf("files"));
                        
		} catch (Exception ex) {
			System.out.println("Erreur d'édition l'affiche engin car "+ex.getMessage());
		}
                System.out.println("=================> "+cheminContrat);
            return cheminContrat;
		
	} 
    
}
