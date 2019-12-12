/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.pdf;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.Engin;


/**
 *
 * @author yassine
 */
 
  
public class test {
    
 public static final String DEST = "D:/barcode_table.pdf";
	public static void main(String[] args) {
           // String chemin = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
       /*  String  chemin = ConstanteMb.getRepertoire();
		System.out.println("chemin : "+chemin);
         
            String path=new File("webapp/images/logo.png").getAbsolutePath();
		System.out.println("path : "+path);
                Engin e = new Engin();
                e.setCode("J55");
                FicheEngin fg = new FicheEngin();
            */
         Fonction f = new Fonction();
         f.setFonction("Ingénieur Systéme d'information");
            ArrayList<Salarie> salaries = new ArrayList<Salarie>();
            for (int i = 0; i < 56; i++) { 
            Salarie s = new Salarie();
            s.setNom("JEDDI ALAOUI ABDELAOUI");
            s.setPrenom("Ahmed Yassine Wali");
            s.setFonction(f);
            s.setMatricule((i*i)+"");
            s.setCin("SS138"+i*i);
            System.out.println("ma.bservices.tgcc.pdf.Badge.editeBadge() matricule :"+s.getMatricule());
                salaries.add(s);
            }
         Badge b = new Badge();
                 b.editeBadge("D:", salaries);

	} 
        
}
