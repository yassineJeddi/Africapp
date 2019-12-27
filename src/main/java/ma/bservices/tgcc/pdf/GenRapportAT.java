/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import ma.bservices.beans.DetailAT;
import ma.bservices.mb.services.ConstanteMb;

/**
 *
 * @author yassine
 */
public class GenRapportAT {
    
    public  String generationRapportAt(String chemin, String nomFichier,DetailAT detailAT ) {
	String cheminFichier="";	
        try {
            
                /*
                chemin=(chemin!=null)?chemin:"";
                nomFichier=(nomFichier!=null)?nomFichier:"";
                Ville=(Ville!=null)?Ville:"";
                nom=(nom!=null)?nom:"";
                fonction=(fonction!=null)?fonction:"";
                cnss=(cnss!=null)?cnss:"";
                dateDebut=(dateDebut!=null)?dateDebut:new Date(); 
                salaire=(salaire!=null)?salaire:""; 
                */
			//config
			//type d'ecriture
	        Font fontEntet = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	        Font fonttitre = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	        Font fontTitreTab = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	        Font fontVal = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL); 
	        Font fontcontent = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); 
	        Font fontP = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Chunk titreUnderline = new Chunk("");
                BaseColor baseColorGray = new BaseColor(223,223,223);
                
                
                Paragraph ent       = new Paragraph("",fontEntet);
                Paragraph titreP    = new Paragraph("",fontEntet);
                Paragraph contentP  = new Paragraph("",fontEntet);
                
		PdfPTable table = new PdfPTable(2);
                PdfPCell c1 = new PdfPCell(titreP);
                PdfPCell c2 = new PdfPCell(titreP);
                PdfPCell c3 = new PdfPCell(titreP);
                PdfPCell c4 = new PdfPCell(titreP);
                PdfPCell c5 = new PdfPCell(titreP);
                PdfPCell c6 = new PdfPCell(titreP);
                
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
			Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                //creation d'un document de type paragraphe
                        PdfWriter writer ;
			writer=PdfWriter.getInstance(document, new FileOutputStream(fileName));
			
			document.open(); 
                        table = new PdfPTable(3);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        String imageFile = new File(ConstanteMb.getRepertoire()+"/SOURCES/logo_at.png").getAbsolutePath();
                        Image img = Image.getInstance(imageFile);
                        img.scaleAbsolute(159, 47);
                        
                        c1 = new PdfPCell(img, true);
                        c2 = new PdfPCell(new Paragraph("\nRapport Accident de Travail ",fontEntet));
                        c3 = new PdfPCell(new Paragraph("\n Référence : FR 23 PS SMP\n Version : 02",fontEntet));
                        
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(1);
			
			c2.setPadding(0);
                        c2.setHorizontalAlignment(1);
                        c2.setVerticalAlignment(1);
                        
			c3.setPadding(0);
                        c3.setHorizontalAlignment(0);
                        c3.setVerticalAlignment(1);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
                         
			document.add(table);
                        
			document.add(vide);
                        
                        table = new PdfPTable(2);
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1 = new PdfPCell(new Paragraph("Projet : "+detailAT.getAccidentTravail().getChantier().getCode()+" \n ",fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("Date & Heure : "+detailAT.getAccidentTravail().getDateAccident()+" \n ",fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("Directeur Projet /ou Chef de Projet :  \n ",fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("Chef de chantier : \n ",fontTitreTab));
                        
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorder(0);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorder(0);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorder(0);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorder(0);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
                                                
			document.add(table);
                        
			document.add(vide);
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Victime ",fonttitre));
                         
			c1.setPadding(1);
                        c1.setPaddingBottom(3);
                        c1.setHorizontalAlignment(1);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        table = new PdfPTable(6);
                        
                        c1 = new PdfPCell(new Paragraph("Nom & Prénom \n " ,fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("Sexe \n ",fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("Qualification \n ",fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("Date d’embauche \n ",fontTitreTab));
                        c5 = new PdfPCell(new Paragraph("N°CIN \n ",fontTitreTab));
                        c6 = new PdfPCell(new Paragraph("N° CNSS \n ",fontTitreTab)); 
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                        
                        c6.setPadding(1); 
                        c6.setPaddingBottom(1);
                        c6.setHorizontalAlignment(1);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
                        
                        
                        c1 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getNom()
                                                        +" "+detailAT.getAccidentTravail().getSalarie().getPrenom()+" \n ",fontVal));
                        c2 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getEtat().getEtat()+" \n ",fontVal));
                        c3 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getFonction().getFonction()+" \n ",fontVal));
                        c4 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getDateNaissance()+" \n ",fontVal));
                        c5 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getCin()+" \n ",fontVal));
                        c6 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getCnss()+" \n ",fontVal));
                        
                         table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                        
                        c6.setPadding(1); 
                        c6.setPaddingBottom(1);
                        c6.setHorizontalAlignment(1);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
                        
                        c1 = new PdfPCell(new Paragraph("Adresse \n ",fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("N° de téléphone \n ",fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("Nationalité  \n ",fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("Situation familiale \n ",fontTitreTab));
                        c5 = new PdfPCell(new Paragraph("Nombre d’épouse \n ",fontTitreTab));
                        c6 = new PdfPCell(new Paragraph("Nombre Enfants \n ",fontTitreTab)); 
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                        
                        c6.setPadding(1); 
                        c6.setPaddingBottom(1);
                        c6.setHorizontalAlignment(1);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
                        
                        
                        c1 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getAdresse()+" \n ",fontVal));
                        c2 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getGsm()+" \n ",fontVal));
                        c3 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getNationalite().getNationalite()+" \n ",fontVal));
                        c4 = new PdfPCell(new Paragraph(detailAT.getAccidentTravail().getSalarie().getSituationFamiliale().getSituationFamiliale()+" \n ",fontVal));
                        c5 = new PdfPCell(new Paragraph(" \n ",fontTitreTab));
                        c6 = new PdfPCell(new Paragraph(((detailAT.getAccidentTravail().getSalarie().getNombreEnfants()!=null)?detailAT.getAccidentTravail().getSalarie().getNombreEnfants():"0")+" \n ",fontVal)); 
                        
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                        
                        c6.setPadding(1); 
                        c6.setPaddingBottom(1);
                        c6.setHorizontalAlignment(1);
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
                        
			document.add(table);
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Cause AT ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        table = new PdfPTable(5);
                        
                        c1 = new PdfPCell(new Paragraph("Chute en \n hauteur : "+((detailAT.getChuteEnHaut())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("Chute d’objet : "+((detailAT.getChuteDObjet())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("Trébuchement : "+((detailAT.getTrebuchement())?"OUI \n ":"NON \n "),fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("Manutention \n manuelle : "+((detailAT.getManutentionManuelle())?"OUI \n ":"NON \n "),fontTitreTab));
                        c5 = new PdfPCell(new Paragraph("Manutention mécanique : "+((detailAT.getManutentionMecanique())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                        c5.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
                        
			document.add(table);
                        
                        table = new PdfPTable(1);
                        
                        c1 = new PdfPCell(new Paragraph(" Cause probable : "+((detailAT.getCauseProbable()!=null)?detailAT.getCauseProbable():"")+" \n ",fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorTop(BaseColor.WHITE);
                                                
			table.addCell(c1);
                        
			document.add(table);
                        
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Lieu AT ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        
                        table = new PdfPTable(3);
                        
                        c1 = new PdfPCell(new Paragraph("A l’intérieur des lieux  de travail : "+((detailAT.getInterieurLieuTravail())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("En mission : "+((detailAT.getMission())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("Trajet : "+((detailAT.getTrajet())?"OUI \n ":"NON \n "),fontTitreTab)); 
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        c2.setBorderColorLeft(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorLeft(BaseColor.WHITE);
                        
                        
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
                        
			document.add(table);
                        
                        table = new PdfPTable(1);
                        
                        c1 = new PdfPCell(new Paragraph(" Indiquer le lieu précis : "+((detailAT.getLieuPrecis()!=null)?detailAT.getLieuPrecis():"")+" \n ",fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorTop(BaseColor.WHITE);
                                                
			table.addCell(c1);
                        
			document.add(table);
                        
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Description AT ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph((" "+((detailAT.getDescription()!=null)?detailAT.getDescription():"")+" \n "),fontVal));
                         
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(0);
                        c1.setPaddingBottom(3);
			
			table.addCell(c1);
                        
                      
                        c1 = new PdfPCell(new Paragraph((" "+((detailAT.getDecriptionArabe()!=null)?detailAT.getDecriptionArabe():"")+" \n ")+" \n ",fontVal));
                         
			c1.setPadding(0); 
                        c1.setHorizontalAlignment(2);
                        c1.setPaddingBottom(3); 
			
			table.addCell(c1);
                        
			document.add(table);
                         
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Nature de la blessure ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        table = new PdfPTable(5);
                        
                        c1 = new PdfPCell(new Paragraph(" Plaie simple : "+((detailAT.getPlaieSimple())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" Plaie grave : "+((detailAT.getPlaieGrave())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph(" Contusion : "+((detailAT.getContusion())?"OUI \n ":"NON \n "),fontTitreTab));
                        c4 = new PdfPCell(new Paragraph(" Brûlure simple : "+((detailAT.getBruleurSimple())?"OUI \n ":"NON \n "),fontTitreTab));
                        c5 = new PdfPCell(new Paragraph(" Brûlure grave : "+((detailAT.getBruleurGrave())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
                        
                        c1 = new PdfPCell(new Paragraph(" Piqûre : "+((detailAT.getPiqure())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" Entorse : "+((detailAT.getEntorse())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph(" Fracture : "+((detailAT.getFracture())?"OUI \n ":"NON \n "),fontTitreTab));
                        c4 = new PdfPCell(new Paragraph(" Douleur : "+((detailAT.getDouleur())?"OUI \n ":"NON \n "),fontTitreTab));
                        c5 = new PdfPCell(new Paragraph(" Luxation : "+((detailAT.getLuxation())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
                        
			document.add(table); 
                        
                        table = new PdfPTable(5);
                        
                        c1 = new PdfPCell(new Paragraph(" Sectionnement : "+((detailAT.getSectionnement())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" Ecrasement : "+((detailAT.getEcrasement())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph(" Pénétration de corps étranger : "+((detailAT.getPenetrationCorpEtranger())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3); 
                        
			document.add(table);
                        
                        table = new PdfPTable(1);
                        c1 = new PdfPCell(new Paragraph(" Autres : "+(((detailAT.getAutreNature()!=null)?detailAT.getAutreNature():"")+" \n "),fontTitreTab));
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                            
			table.addCell(c1);
			document.add(table);
                        
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Siège de la blessure ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        table = new PdfPTable(6);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1 = new PdfPCell(new Paragraph(" Yeux D : "+((detailAT.getYeuxD())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" Yeux G : "+((detailAT.getYeuxG())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph(" Cuisse D : "+((detailAT.getCuisseD())?"OUI \n ":"NON \n "),fontTitreTab));
                        c4 = new PdfPCell(new Paragraph(" Cuisse G : "+((detailAT.getCuisseG())?"OUI \n ":"NON \n "),fontTitreTab));
                        c5 = new PdfPCell(new Paragraph(" Main D : "+((detailAT.getMainD())?"OUI \n ":"NON \n "),fontTitreTab));
                        c6 = new PdfPCell(new Paragraph(" Main G : "+((detailAT.getMainG())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorRight(BaseColor.WHITE);
                        
                        c6.setPadding(1); 
                        c6.setPaddingBottom(1);
                        c6.setHorizontalAlignment(0);
                        c6.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
                        
                        c1 = new PdfPCell(new Paragraph(" Pied D : "+((detailAT.getPiedD())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" Pied G : "+((detailAT.getPiedG())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph(" Bras D : "+((detailAT.getBrasD())?"OUI \n ":"NON \n "),fontTitreTab));
                        c4 = new PdfPCell(new Paragraph(" Bras G : "+((detailAT.getBrasG())?"OUI \n ":"NON \n "),fontTitreTab));
                        c5 = new PdfPCell(new Paragraph(" Jambe D : "+((detailAT.getJambeD())?"OUI \n ":"NON \n "),fontTitreTab));
                        c6 = new PdfPCell(new Paragraph(" Jambe G : "+((detailAT.getJambeG())?"OUI \n ":"NON \n "),fontTitreTab));
     
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorRight(BaseColor.WHITE);
                        
                        c6.setPadding(1); 
                        c6.setPaddingBottom(1);
                        c6.setHorizontalAlignment(0);
                        c6.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
                        
			document.add(table); 
                        
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Mesures prises immédiatement ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        table = new PdfPTable(2);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1 = new PdfPCell(new Paragraph(" Secours internes : "+((detailAT.getSecourInterne())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" Secours externes : "+((detailAT.getSecourExterne())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
                        
			document.add(table);
                        
                        
                        table = new PdfPTable(1);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                      
                        c1 = new PdfPCell(new Paragraph("Suites ",fonttitre));
                         
			c1.setPadding(1); 
                        c1.setHorizontalAlignment(1);
                        c1.setPaddingBottom(3);
                        c1.setBackgroundColor(baseColorGray);
			c1.setBorderColor(BaseColor.BLACK);
			
			table.addCell(c1);
                        
			document.add(table);
                        
                        table = new PdfPTable(5);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1 = new PdfPCell(new Paragraph(" Hospitalisation : "+((detailAT.getHospitalisation())?"OUI \n ":"NON \n "),fontTitreTab));
                        c2 = new PdfPCell(new Paragraph(" AT sans Arrêt : "+((detailAT.getAtAvecArret())?"OUI \n ":"NON \n "),fontTitreTab));
                        c3 = new PdfPCell(new Paragraph(" AT avec Arrêt : "+((detailAT.getAtSansArret())?"OUI \n ":"NON \n "),fontTitreTab));
                        c4 = new PdfPCell(new Paragraph(" Nombre de jour : "+detailAT.getNbrJour(),fontTitreTab));
                        c5 = new PdfPCell(new Paragraph(" Décès : "+((detailAT.getDeces())?"OUI \n ":"NON \n "),fontTitreTab));
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(0);
                        c1.setBorderColorBottom(BaseColor.WHITE);
                        c1.setBorderColorRight(BaseColor.WHITE);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(0);
                        c2.setBorderColorBottom(BaseColor.WHITE);
                        c2.setBorderColorRight(BaseColor.WHITE);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(0);
                        c3.setBorderColorBottom(BaseColor.WHITE);
                        c3.setBorderColorRight(BaseColor.WHITE);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(0);
                        c4.setBorderColorBottom(BaseColor.WHITE);
                        c4.setBorderColorRight(BaseColor.WHITE);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(0);
                        c5.setBorderColorBottom(BaseColor.WHITE);
                        c5.setBorderColorRight(BaseColor.WHITE);
                                                
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
                        
			document.add(table);
                        
                        table = new PdfPTable(5);
                        
                        table.setWidthPercentage(100);
                        table.setSpacingBefore(0f);
                        table.setSpacingAfter(0f);
                        
                        c1 = new PdfPCell(new Paragraph(" \n ",fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("Nom & Prénom ",fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("Fonction",fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("Date",fontTitreTab));
                        c5 = new PdfPCell(new Paragraph("Visa",fontTitreTab));
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        c1.setBorder(0);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                                             
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
                        
                        c1 = new PdfPCell(new Paragraph("Rédigé par \n ",fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        c5 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                                             
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5); 
                        
                        c1 = new PdfPCell(new Paragraph("Vérifié par  \n ",fontTitreTab));
                        c2 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        c3 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        c4 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        c5 = new PdfPCell(new Paragraph("\n ",fontTitreTab));
                        
                        c1.setPadding(1); 
                        c1.setPaddingBottom(1);
                        c1.setHorizontalAlignment(1);
                        
                        c2.setPadding(1); 
                        c2.setPaddingBottom(1);
                        c2.setHorizontalAlignment(1);
                        
                        c3.setPadding(1); 
                        c3.setPaddingBottom(1);
                        c3.setHorizontalAlignment(1);
                        
                        c4.setPadding(1); 
                        c4.setPaddingBottom(1);
                        c4.setHorizontalAlignment(1);
                        
                        c5.setPadding(1); 
                        c5.setPaddingBottom(1);
                        c5.setHorizontalAlignment(1);
                                             
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5); 
                        
			document.add(vide);
                        
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
}
