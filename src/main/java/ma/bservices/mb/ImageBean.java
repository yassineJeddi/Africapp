/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId;
import ma.bservices.beans.Document;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrateur
 */
@Component
@ManagedBean
@SessionScoped

public class ImageBean implements Serializable {

    private StreamedContent image = new DefaultStreamedContent();
    private StreamedContent contrat = new DefaultStreamedContent();
    private StreamedContent document = new DefaultStreamedContent();
    private StreamedContent bl = new DefaultStreamedContent();

    public StreamedContent getContrat() {
        try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                contrat = new DefaultStreamedContent();
            }
            
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            //String chemin= context.getRealPath("/");
            String chemin = ConstanteMb.getRepertoire();
            String fichier = context.getRequestParameterMap().get("path");
            InputStream stream = new FileInputStream(chemin + "/" + fichier);
            System.out.println("fichier Contrat to display" + fichier);

            contrat = new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier),fichier);
            
        } catch (Exception e) {
            System.out.println("erreur to display contrat \n\n" + e.getMessage());
        }
        return contrat;
    }

    public void setContrat(StreamedContent contrat) {
        this.contrat = contrat;
    }

    public StreamedContent getDocument() {
        try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                document = new DefaultStreamedContent();
            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            //String chemin= context.getRealPath("/");
            String chemin = ConstanteMb.getRepertoire();
            String fichier = context.getRequestParameterMap().get("path");
            InputStream stream = new FileInputStream(fichier);

            document = new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public void setDocument(StreamedContent document) {
        this.document = document;
    }

    public StreamedContent getBl() {
        return bl;
    }

    public void setBl(StreamedContent bl) {
        this.bl = bl;
    }

    public StreamedContent getBon(){
     
     try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            //String chemin= context.getRealPath("/");
            String chemin  = ConstanteMb.getRepertoire();
            String fichier = context.getRequestParameterMap().get("path");
            System.out.println("file " + fichier);
           // String codeCurrent = "resources/document/bonTransfert" + refTr + ".pdf";
            InputStream stream = new FileInputStream( chemin + "/" + fichier);

            image = new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
      public StreamedContent getDocumentMensuel(){
     
     try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            //String chemin= context.getRealPath("/");
             String chemin = ConstanteMb.getRepertoire();
            String fichier = context.getRequestParameterMap().get("path");
           // String codeCurrent = "resources/document/bonTransfert" + refTr + ".pdf";
            InputStream stream = new FileInputStream( chemin + "/" + fichier);

            image = new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    
    public StreamedContent getImage() {
        try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            //String chemin= context.getRealPath("/");
            String chemin = ConstanteMb.getRepertoire();
            String fichier = context.getRequestParameterMap().get("path");
            InputStream stream = new FileInputStream(chemin + "/" + fichier);

            image = new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public boolean fileExists(String fichier)
    {
    String chemin = ConstanteMb.getRepertoire();
   
    File f = new File(chemin + "/" + fichier);
if(f.exists() && !f.isDirectory()) { 
    System.out.println("fichier :" +chemin + "/" + fichier + " Existe");
        return Boolean.TRUE;
        
    
    }
else {
    System.out.println("fichier :" +chemin + "/" + fichier + "n'existe pas");
    return Boolean.FALSE;

}}
     public StreamedContent getFile(String fichier,String titre) throws AbortProcessingException {
        try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            //ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            //String chemin= context.getRealPath("/");
            String chemin = ConstanteMb.getRepertoire();
            String[] filename;
            String[] tabFile;
            String ext="pdf";
            InputStream stream;
//  String fichier = context.getRequestParameterMap().get("path");
           
         
            try{
                stream = new FileInputStream(chemin + "/" + fichier);
                        }
            catch(IOException e) {
                stream=null;
            
            }
         if (stream==null)
                         //FacesUtil.addError("Erreur de Telechargement");
                throw new AbortProcessingException();
            
                 
          if(!"".equals(fichier)&& stream!=null)  
                filename=fichier.split("/");
          else throw new AbortProcessingException();
            if (filename.length>0){
             tabFile= filename[filename.length-1].split(".");
                if (tabFile.length>0)
                    ext = tabFile[tabFile.length-1];     
            System.out.println(ext + " ext " + "filename " + " " +filename[filename.length-1]);
            
            }
            
            
                      
            image = new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier),titre+"."+ext);
            
                    return image;

            
        } catch (Exception e) {
             Module.message(3, "Impossible de Telecharger le document","Document Introuvalble ! ");
            System.out.println("Erreur de Telechargement de document");
            return new DefaultStreamedContent();
        }
    }
    
    /*public StreamedContent getImage(String Path)
     {
           
     try {
     if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
     System.out.println("phase is render response");
     return new DefaultStreamedContent();
     }
     String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
     String fullfilepathname = absoluteWebPath + "/" + Path;
     //    File webRoot = new File(absoluteWebPath + "/" + path);
     //            System.out.println("file with absolute path " + webRoot.getAbsolutePath());
     //            byte[] buffer = Files.readAllBytes(webRoot.toPath());
     //            ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length);
     //            baos.write(buffer, 0, buffer.length);
     //            System.out.println("\n\n\nbuffer " + Arrays.toString(buffer) + "\n\n");

     String mimeType = URLConnection.guessContentTypeFromName(fullfilepathname);
     //            System.out.println("1 - mime Type " + mimeType);
     InputStream stream = new FileInputStream(fullfilepathname);
     //            Byte[] byteObjects = new Byte[buffer.length];
     //            inputStream = new ByteArrayInputStream(ArrayUtils.toPrimitive(byteObjects));
     //            System.out.println("\n\n input stream " + inputStream.toString() + " \n");


     StreamedContent result = new DefaultStreamedContent(stream, mimeType);
     System.out.println("\n\n result " + result.getName() + " \n");
     return result;
     } catch (IOException ex) {
     System.out.println("erreur buffer output stream\n\n\t" + ex.getMessage() + "\n\n end erreur outputStream");
     }
     return null;

     }*/

    public StreamedContent setImage(StreamedContent image) {
        return this.image = image;
    }

    /**
     * get output stream from file input
     *
     * @param path to get
     * @return output stream
     */
    public StreamedContent displayFile(String path) {
        System.out.println("path in  " + path);
        try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            //String chemin= context.getRealPath("/");
            String chemin = ConstanteMb.getRepertoire();
            String fichier = path;
            InputStream stream = new FileInputStream(chemin + "/" + fichier);

            return new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(fichier));
        } catch (IOException ex) {
            System.out.println("erreur buffer output stream\n\n\t" + ex.getMessage() + "\n\n end erreur outputStream");
        }
        return null;

    }
}
