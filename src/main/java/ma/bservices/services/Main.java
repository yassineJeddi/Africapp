//package ma.bservices.services;
//
//import java.io.Serializable;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.HashMap;
//import ma.bservices.constantes.Constantes;
////import org.alfresco.service.cmr.repository.InvalidNodeRefException;
////import org.alfresco.service.cmr.repository.NodeRef;
////import org.alfresco.service.namespace.QName;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
////import com.rivetlogic.core.cma.exception.AuthenticationFailure;
////import com.rivetlogic.core.cma.exception.CmaRuntimeException;
////import com.rivetlogic.core.cma.exception.InvalidTicketException;
////import com.rivetlogic.core.cma.impl.AuthenticationServiceImpl;
////import com.rivetlogic.core.cma.impl.NodeServiceImpl;
////import com.rivetlogic.core.cma.repo.Ticket;
//
//public class Main {
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//
//        /**
//         * ---------------------------------------- Création des dossiers des
//         * salariées sur Alfresco ----------------------------------------*
//         */
//        ConfigurableApplicationContext configurationApplicationContext = new ClassPathXmlApplicationContext(Constantes.APPLICATION_CONTEXT);
////        AuthenticationServiceImpl authenticationService = (AuthenticationServiceImpl) configurationApplicationContext.getBean("authenticationService");
//        NodeServiceImpl nodeService = (NodeServiceImpl) configurationApplicationContext.getBean("nodeService");
//
//        try {
//
//            /* Identifiants Alfresco */
//            String urlAlfresco = "http://192.168.21.3:8088/alfresco/service";
//            String nomUtilisateurAlfresco = "admin";
//            String motDePasseAlfresco = "5176edcd09";
//
//            Ticket ticket = authenticationService.authenticate(urlAlfresco, nomUtilisateurAlfresco, motDePasseAlfresco.toCharArray());
//            NodeRef nodeRefDossiersSalaries = new NodeRef("workspace://SpacesStore/fffacd10-356a-482d-abb1-fb3802fe1488");
//            String idNodeRefPhotoSalarie = "ccf0f260-2e56-489d-8774-ed186c4304ae";
//
//            /* Identifiants SQLSERVER */
//            String bdUrl = "jdbc:sqlserver://192.168.21.4:1433;databaseName=Bpaie";
//            String nomUtilisateur = "sa";
//            String motDePasse = "tgcc2013#";
//
//            Connection connection = DriverManager.getConnection(bdUrl, nomUtilisateur, motDePasse);
//
//            /* Liste des salariés */
//            Statement statement = connection.createStatement();
//            ResultSet resultSetSalaries = statement.executeQuery("select ID from SALARIE where etat_ID=7");
//
//            /* Statement pour insert et update */
//            PreparedStatement preparedStatement = null;
//
//            /* Création des dossiers des salariés au niveau Alfresco et base de données */
//            while (resultSetSalaries.next()) {
//
//                Integer idSalarie = Integer.parseInt(resultSetSalaries.getString(1));
//
//                System.out.println("IdSalarie : " + idSalarie);
//
//                /* Etape 1 : créer un nouveau dossier salarie dans la base de données */
//                Integer idDossierSalarie = null;
//                preparedStatement = connection.prepareStatement("INSERT into DOSSIERSALARIE(NODEREFPHOTO) values(?)", PreparedStatement.RETURN_GENERATED_KEYS);
//                preparedStatement.setString(1, idNodeRefPhotoSalarie);
//                preparedStatement.executeUpdate();
//                ResultSet keyResultSet = preparedStatement.getGeneratedKeys();
//
//                if (keyResultSet.next()) {
//
//                    idDossierSalarie = keyResultSet.getInt(1);
//
//                }
//
//                keyResultSet.close();
//                preparedStatement.close();
//
//                /* Etape 2 : creation du dossier du salarie dans Alfresco */
//                NodeRef nodeRefDossierSalarie = null;
//                String nomDossierSalarie = "salarie_" + idDossierSalarie;
//                nodeRefDossierSalarie = nodeService.createFolder(ticket, nomDossierSalarie, nodeRefDossiersSalaries, new HashMap<QName, Serializable>());
//                nodeService.rename(ticket, nodeRefDossierSalarie, nomDossierSalarie);
////			String nomDossierSalarie = "salarie_" + idDossierSalarie;
////			nodeRefDossierSalarie =	nodeService.getChildByName(ticket, new NodeRef("workspace://SpacesStore/fffacd10-356a-482d-abb1-fb3802fe1488"),ContentModel.ASSOC_CONTAINS, nomDossierSalarie);
//
//                /* Etape 3 : Modifier le Dossier du salarié en lui ajoutant le le NodeRef du dossier sur Alfresco dans la base de données  */
//                preparedStatement = connection.prepareStatement("UPDATE DOSSIERSALARIE set NODEREFDOSSIER = '" + nodeRefDossierSalarie.getId() + "' where ID = " + idDossierSalarie);
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//
//                /* Etape 4 : affectation du dossier au salarié dans la base de donées */
//                preparedStatement = connection.prepareStatement("UPDATE SALARIE set dossiersalarie_ID =" + idDossierSalarie + " where ID = " + idSalarie);
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//
//            }
//
//            resultSetSalaries.close();
//            statement.close();
//            connection.close();
//
//        } catch (AuthenticationFailure e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InvalidNodeRefException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InvalidTicketException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (CmaRuntimeException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        /**
//         * ---------------------------------------- Insertion des salariées au
//         * niveau divalto -----------------------------------------*
//         */
////	    
////		 SessionFactory sf = new AnnotationConfiguration().configure("/ma/bservices/services/hibernate-context.xml").buildSessionFactory();	
////      Query query=sf.openSession().createQuery("FROM Salarie where id>=1016857 and id<=1016953 order by id");
////		//query.setParameter(0, idSalarie, Hibernate.INTEGER);
////      List<Salarie> listeSalaries=query.list();
////		
////      for(int i=0;i<listeSalaries.size();i++){
////        Salarie salarieCree=listeSalaries.get(i);
////        String chaineDateNaissance="";
////        if(salarieCree.getDateNaissance()!=null ){
////        	chaineDateNaissance = salarieCree.getDateNaissance().toString().substring(0, 10).replaceAll("-", "");
////        }
////        String nationaliteSalarie="";
////        if(salarieCree.getNationalite()!=null ){
////        	nationaliteSalarie = salarieCree.getNationalite().getCodeDiva();
////        }
////        String paysSalarie="";
////        if(salarieCree.getPays()!=null ){
////        	paysSalarie = salarieCree.getPays().getCodeDiva();
////        }
////        
////        String statutSalarie="";
////        if(salarieCree.getFonction()!=null ){
////        	statutSalarie = salarieCree.getFonction().getCodeDiva().substring(0,1);
////        }
////        
////        String fonctionSalarie="";
////        if(salarieCree.getFonction()!=null ){
////        	fonctionSalarie = salarieCree.getFonction().getCodeDiva().trim();
////        }
////        
////        String  mpSalarie="";
//////        if(salarieCree.getFonction()!=null ){
//////        	mpSalarie = salarieCree.getModePaiement().getCodeDiva();
//////        }
//////        
////        Map<String, String> mapAjouterSalarieWS=new HashMap<String, String>();
////		SalarieService salarieService=new SalarieService();
////		mapAjouterSalarieWS=salarieService.ajouterSalarieWS(
////				salarieCree.getMatricule(), salarieCree.getCivilite().getCodeDiva(),salarieCree.getSituationFamiliale().getCodeDiva(), 
////				salarieCree.getNom(), salarieCree.getPrenom(), salarieCree.getCin(), salarieCree.getCnss(), chaineDateNaissance, 
////				salarieCree.getLieuNaissance(), nationaliteSalarie, paysSalarie, 
////				salarieCree.getAdresse(), salarieCree.getVille(),salarieCree.getTelephone(), salarieCree.getGsm(), salarieCree.getEmail(), 
////				salarieCree.getRib(), salarieCree.getNomBanque(), statutSalarie,
////				fonctionSalarie, mpSalarie);
////		
////		String reponseDiva=mapAjouterSalarieWS.get("referenceSalarieDiva");
////		System.out.println("reponse ajout individu sur Divalto: "+reponseDiva);
////		
////		if(mapAjouterSalarieWS.get("erreur").equals("1")){
////			break;
////		}
////		//mapAjouterSalarieWS=salarieService.ajouterEnfantSalarieWS(salarieCree.getMatricule(), "test2", "20010101");
////		//String reponseDiva=mapAjouterSalarieWS.get("referenceEnfantSalarieDiva");
////		//System.out.println("reponse ajout enfant sur Divalto: "+reponseDiva);
////      
////      }
////		
//        /**
//         * ---------------------------------------- Insertion des contrats au
//         * niveau DIVALTO ------------------------------------------- *
//         */
////		
////		 SessionFactory sf = new AnnotationConfiguration().configure("/ma/bservices/services/hibernate-context.xml").buildSessionFactory();	
////		 Query query=sf.openSession().createQuery("FROM Contrat  WHERE salarie.id>=1016857 and salarie.id<=1016953 order by salarie.id ");
////		 //query.setParameter(0, 2, Hibernate.INTEGER);
////		 List<Contrat> listecontrats=query.list();
////		
////		 for(int i=0;i<listecontrats.size();i++){
////		
////		 Contrat contrat=listecontrats.get(i);
////		 System.out.println("contrat: "+contrat.getId());
////		 String
////		 dateDebutContrat=contrat.getDateEmbauche().toString().substring(0,10).replaceAll("-", "");
////		 String dateFinContrat=contrat.getDateFin().toString().substring(0,10).replaceAll("-", "");
////		 String typeContrat=contrat.getTypeContrat().getCodeDiva();
////		 //String tauxHoraire=contrat.getTauxHoraire().toString();
////		 Map<String, String> mapAjouterContratWS=new HashMap<String, String>();
////		 SalarieService salarieService=new SalarieService();
////		 mapAjouterContratWS=salarieService.ajouterContratSalarieWS(contrat.getSalarie().getMatricule(),
////		 dateDebutContrat,dateFinContrat, typeContrat, contrat.getTauxHoraire().toString());
////		
////		 String
////		 referenceContratDiva=mapAjouterContratWS.get("referenceContratDiva");
////		 System.out.println("reponse ajout contrat sur Divalto: "+referenceContratDiva);
////		
////		
////		 }
////		 sf.close();
//        // ---- Fin Insertion des contrats au niveau DIVALTO ----
//        /**
//         * --------------------------------------- TEST DES WEB SERVICES
//         * ---------------------------------------*
//         */
//        /**
//         * RH *
//         */
//        SalarieService salarieService = new SalarieService();
//
////		salarieService.ajouterSalarieWS("10000003", "01", "2", "test3", "test3", "t123456", "123456785", "19800201", "Casablanca", "MA", "MA", "abdelmoumen", "Casablanca", "05555555555", "0666666666", "test3@test3.com", "190780211114968401000957", "BP", "P", "P04", "E000");
////      salarieService.modifierSalarieWS("10000001", "01", "2", "Alaoui", "amine", "aj123456", "123456785", "19800230", "benimellal", "MA", "MA", "bd abdelmoumen", "Casa", "0522666777", "0666458721", "abdel@test.com", "190780211114968401000957", "bp", "P", "P04", "E000");
////      salarieService.ajouterEnfantSalarieWS("10000001", "Mohamed", "20000125");
////      salarieService.modifierEnfantSalarieWS("10000003", "Ahmed", "20000125","p");
////      salarieService.ajouterContratSalarieWS("1015461", "20131215","20141215", "fcdd", "14");
////      salarieService.modifierFonctionTauxHoraireWS("10000001", "P04", "12");
////      salarieService.modifierFonctionTauxHoraireWS("1015461", "P05", "15");
////      salarieService.modifierContratSalarieWS("1015461", "20131115","20131115", "","");
////      salarieService.modifierContratSalarieWS("1015461", "20131022","20131030", "fcdd","20131030");
//        /**
//         * ACHAT *
//         */
////      AchatService achatService=new AchatService();
////      achatService.commandeInterne_trsDepot("MPMDBACONCOLFN250;10,0", "20131015", "CHAN0001", "test");
////      achatService.getCommande("1339568");
////      achatService.validationBLWS(numeroBonCommande, numeroBonLivraison, dateLiv, commentaire, articlesQuantites);
//    }
//
//}
