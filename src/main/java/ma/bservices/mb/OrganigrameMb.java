/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.NiveauFonctionService;
import ma.bservices.services.OrganigrameService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.Entity.Organigrame;
import ma.bservices.tgcc.Entity.SalariesNiveau;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;

import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

/**
 *
 * @author airaamane
 */
@Component
@ManagedBean
@ViewScoped
public class OrganigrameMb implements Serializable {

    @ManagedProperty(value = "#{organigrameService}")
    private OrganigrameService organigrameService;

    @ManagedProperty(value = "#{niveauFonctionService}")
    private NiveauFonctionService niveauFonctionService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    private Integer idChantier, idChantierConsult;

    private Integer idNiveau, idNiveauConsult;

    private boolean isChefSelected;

    private List<Salarie> selectedSalaries = new LinkedList<>();

    private List<Salarie> salariesByChantierNiveau = new LinkedList<>();

    private List<Salarie> listeChefsParSalarie = new LinkedList<>();
    private Salarie selectedChefSalarie;
    private Salarie supChef;

    private List<Salarie> salaries_actif = new LinkedList<>();

    // -- ** variables de l'organigrame ** -- //
    private OrganigramNode rootNode;
    private OrganigramNode selection;
    private boolean zoom = false;
    private String style = "width: 100%; text-align:center;";
    private int leafNodeConnectorHeight = 2;
    private boolean autoScrollToSelection = false;

    /**
     * -- getters & setters -- *
     */
    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Integer getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(Integer idNiveau) {
        this.idNiveau = idNiveau;
    }

    public OrganigrameService getOrganigrameService() {
        return organigrameService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public void setOrganigrameService(OrganigrameService organigrameService) {
        this.organigrameService = organigrameService;
    }

    public NiveauFonctionService getNiveauFonctionService() {
        return niveauFonctionService;
    }

    public void setNiveauFonctionService(NiveauFonctionService niveauFonctionService) {
        this.niveauFonctionService = niveauFonctionService;
    }

    public List<Salarie> getSelectedSalaries() {
        return selectedSalaries;
    }

    public Salarie getSelectedChefSalarie() {
        return selectedChefSalarie;
    }

    public void setSelectedChefSalarie(Salarie selectedChefSalarie) {
        this.selectedChefSalarie = selectedChefSalarie;
    }

    public List<Salarie> getListeChefsParSalarie() {
        return listeChefsParSalarie;
    }

    public void setListeChefsParSalarie(List<Salarie> listeChefsParSalarie) {
        this.listeChefsParSalarie = listeChefsParSalarie;
    }

    public List<Salarie> getSalaries_actif() {
        return salaries_actif;
    }

    public void setSalaries_actif(List<Salarie> salaries_actif) {
        this.salaries_actif = salaries_actif;
    }

    public void setSelectedSalaries(List<Salarie> selectedSalaries) {
        this.selectedSalaries = selectedSalaries;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Salarie> getSalariesByChantierNiveau() {
        return salariesByChantierNiveau;
    }

    public void setSalariesByChantierNiveau(List<Salarie> salariesByChantierNiveau) {
        this.salariesByChantierNiveau = salariesByChantierNiveau;
    }

    public Integer getIdChantierConsult() {
        return idChantierConsult;
    }

    public void setIdChantierConsult(Integer idChantierConsult) {
        this.idChantierConsult = idChantierConsult;
    }

    public Integer getIdNiveauConsult() {
        return idNiveauConsult;
    }

    public void setIdNiveauConsult(Integer idNiveauConsult) {
        this.idNiveauConsult = idNiveauConsult;
    }

    public boolean isIsChefSelected() {
        return isChefSelected;
    }

    public void setIsChefSelected(boolean isChefSelected) {
        this.isChefSelected = isChefSelected;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    /* organigram */
    public OrganigramNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }

    public OrganigramNode getSelection() {
        return selection;
    }

    public void setSelection(OrganigramNode selection) {
        this.selection = selection;
    }

    public boolean isZoom() {
        return zoom;
    }

    public Salarie getSupChef() {
        return supChef;
    }

    public void setSupChef(Salarie supChef) {
        this.supChef = supChef;
    }
    
    

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getLeafNodeConnectorHeight() {
        return leafNodeConnectorHeight;
    }

    public void setLeafNodeConnectorHeight(int leafNodeConnectorHeight) {
        this.leafNodeConnectorHeight = leafNodeConnectorHeight;
    }

    public boolean isAutoScrollToSelection() {
        return autoScrollToSelection;
    }

    public void setAutoScrollToSelection(boolean autoScrollToSelection) {
        this.autoScrollToSelection = autoScrollToSelection;
    }

    /**
     * -- end of getters & setters -- *
     */
    @PostConstruct
    public void init() {
        organigrameService = Module.ctx.getBean(OrganigrameService.class);
        niveauFonctionService = Module.ctx.getBean(NiveauFonctionService.class);
        mensuelService = Module.ctx.getBean(MensuelService.class);
        List<Salarie> s_list = new LinkedList<>();
        System.out.println("LIST ACTIVES : ");
        s_list = mensuelService.findAllActifs();
        System.out.println("SIZE OF : " + s_list.size());
        salaries_actif = s_list;
        if (mensuelService.findAll() != null) {
            salaries_actif.addAll(mensuelService.findAll());
        }

    }

    public void initOrganigram(Chantier chantierName) {

        selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);

        rootNode = new DefaultOrganigramNode("root", "Chantier : \n" + chantierName.getCode(), null);
        rootNode.setCollapsible(false);
        rootNode.setDroppable(true);

        List<NiveauFonction> niveauxAll = niveauFonctionService.getAll();

        for (NiveauFonction nf : niveauxAll) {
            List<String> salariesNoms = new LinkedList<>();
            System.out.println("Niveau : " + nf.getNiveau());
            List<Integer> listOfSalaries = organigrameService.findByChantierNiveau(chantierName, nf);

            if (listOfSalaries != null) {
                for (Integer org : listOfSalaries) {
                    Salarie s = salarieService.getSalarie(org);
                    salariesNoms.add(s.getNom() + " " + s.getPrenom().substring(0, 1) + ". (" + s.getFonction().getFonction() + ")");
                   // System.out.println("SALARIE : " + org);
                }
            }
            /*for (String s : salariesNoms) {
                System.out.println("SALARIE : " + s);
            }*/

            addDivision(rootNode, nf.getNiveau(), salariesNoms);

        }

    }

    public boolean checkNodeChef(String s) {

        //System.out.println("SELECTION : " + selection);
        //System.out.println("STRING : " + s);

        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(s);
        List<Organigrame> ls = organigrameService.findOrgsByChantierNiveau(chantier, nf);

        if (ls != null) {
            for (Organigrame org : ls) {
                return org.isIsChef();
            }
        }

        return false;

    }

    public void onChantierChange() {
        //System.out.println("ChantierChanged :: " + idChantier);

        Chantier chantier = chantierService.findById(idChantier);
        selectedSalaries.clear();
        
        initOrganigram(chantier);
        
        salaries_actif = salarieService.salarieByChantier();

    }

    public void onNiveauChange() {
        //System.out.println("NiveauChanged :: " + idNiveau);
        selectedSalaries.clear();
        NiveauFonction niveau = niveauFonctionService.getById(idNiveau);
        Chantier chantier = chantierService.findById(idChantier);

    }

    public void onChantierChangeConsult() {
        //System.out.println("ChantierChanged :: " + idChantierConsult);
    }

    public void onNiveauChangeConsult() {
        salariesByChantierNiveau.clear();
        isChefSelected = false;
        NiveauFonction niveau = niveauFonctionService.getById(idNiveauConsult);
        Chantier chantier = chantierService.findById(idChantierConsult);

        List<Integer> orgs = organigrameService.findByChantierNiveau(chantier, niveau);

        if (orgs != null) {
            for (Integer org : orgs) {
                salariesByChantierNiveau.add(salarieService.getSalarie(org));
//                if (org.isIsChef()) {
//                    isChefSelected = true;
//                }
            }
        }
    }

    public void definirChefEquipe() {

        NiveauFonction niveau = niveauFonctionService.getById(idNiveauConsult);
        Chantier chantier = chantierService.findById(idChantierConsult);

//         String msg = isChefSelected ? "Checked Chef" : "Unchecked Chef";
//        List<Organigrame> orgs = organigrameService.findByChantierNiveau(chantier, niveau);
//
//        for (Organigrame org : orgs) {
//            org.setIsChef(isChefSelected);
//            organigrameService.update(org);
//        }
    }

    //init la liste des chefs par salarie
    public void listeChefSalarie() {
        //System.out.println("LISTING .... ");
        //System.out.println("SELECTION : " + selection.getData().toString());

        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getParent().getData().toString());
        Salarie salarie = null;
        List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);

        if (ls != null) {
            for (Integer i : ls) {
                Salarie s = salarieService.getSalarie(i);
                String ss = s.getNom() + " " + s.getPrenom().substring(0, 1) + ". (" + s.getFonction().getFonction() + ")";
                if (selection.getData().toString().compareTo(ss) == 0) {
                    salarie = s;
                    Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, salarie);
                    if(org.getSuperieur() != null){
                    //System.out.println("CHEF DE CE SALARIE : " + org.getSuperieur());
                    supChef = salarieService.getSalarie(Integer.parseInt(org.getSuperieur()));
                    }
                }
            }
        }

    }

    // ajouter chef a un salarie
    public void addChefSalarie() {
        /*
        System.out.println("ADDING CHEF ...");
        System.out.println("SELECTED CHEF : " + selectedChefSalarie.getNom()) ;
        System.out.println("SELECTION : " + selection.getData().toString());
        System.out.println("NIVEAU : " + selection.getParent().getData().toString());
        */
        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getParent().getData().toString());
        Salarie salarie = null;
        List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);
        if (ls != null) {
            for (Integer i : ls) {
                Salarie s = salarieService.getSalarie(i);
                String ss = s.getNom() + " " + s.getPrenom().substring(0, 1) + ". (" + s.getFonction().getFonction() + ")";
                if (selection.getData().toString().compareTo(ss) == 0) {
                    salarie = s;
                    Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, salarie);
                    org.setSuperieur(selectedChefSalarie.getId().toString());
                    organigrameService.update(org);
                    //System.out.println("LIST OF SUP : " + org.getSuperieur());
                    break;
                }
            }
        }

        if (salarie != null) {
            Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, salarie);
        }

    }

    // ajout de chef par salarie
    public void choisirChefSalarie() {

        listeChefsParSalarie.clear();
        //System.out.println("SELECTION : " + selection.getData().toString());
        //System.out.println("NIVEAU : " + selection.getParent().getData().toString());

        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getParent().getData().toString());
        List<NiveauFonction> nf_list = niveauFonctionService.findByPriority(nf.getPriorite());

        List<Organigrame> listOfOrgs = new LinkedList<>();
        List<Organigrame> listByNiveau = new LinkedList<>();

        for (NiveauFonction nfs : nf_list) {
            listByNiveau = organigrameService.findOrgsByChantierNiveau(chantier, nfs);
            if (listByNiveau != null) {
                listOfOrgs.addAll(listByNiveau);
            }
        }

        if (listOfOrgs != null) {
            for (Organigrame org : listOfOrgs) {
                listeChefsParSalarie.add(org.getSalarie());
            }

        }

        // System.out.println("CHANTIER : " + );
    }

    // ajout salarie a un niveau 
    public void initAddSalarie() {
        //System.out.println("SELECTION : " + selection.getData().toString());
        selectedSalaries.clear();

        Chantier chantier = chantierService.findById(idChantier);

        NiveauFonction nf = niveauFonctionService.getByString(selection.getData().toString());

        List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);

        if (ls != null) {

            for (Integer i : ls) {
                Salarie s = salarieService.getSalarie(i);
                selectedSalaries.add(s);
            }
        }

        //System.out.println("INIT");
    }

    public void construireOrganigrameChantier() {

        // NiveauFonction niveau = niveauFonctionService.getById(idNiveau);
        // Chantier chantier = chantierService.findById(idChantier);
        Organigrame firstOrg = new Organigrame();

        // firstOrg.setChantier(chantier);
        //firstOrg.setNiveau(niveau);
//        for (Salarie s : selectedSalaries) {
//
//            if (organigrameService.findByChantierNiveauSalarie(chantier, niveau, s) == null) {
//                System.out.println("NO ENTRY FOR CHANTIER NIVEAU SALARIE ENTERED!");
//                firstOrg.setSalarie(s);
//                organigrameService.save(firstOrg);
//            }
//        }
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès!", "Les salariés choisis sont affecté au niveau suivant: " + niveau.getNiveau()));
    }

    //** ORGANIGRAM STRUCTURE **//
    protected OrganigramNode addDivision(OrganigramNode parent, String name, List<String> salaries) {
        OrganigramNode divisionNode = new DefaultOrganigramNode("division", name, parent);
        divisionNode.setDroppable(true);
        divisionNode.setDraggable(true);
        divisionNode.setSelectable(true);

        if (salaries != null) {
            for (String salarie : salaries) {
                //System.out.println("NOT NULL");
                OrganigramNode employeeNode = new DefaultOrganigramNode("employee", salarie, divisionNode);
                employeeNode.setDraggable(true);
                employeeNode.setSelectable(true);
            }
        }

        return divisionNode;
    }

    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' moved from " + event.getSourceOrganigramNode().getData() + " to '" + event.getTargetOrganigramNode().getData() + "'");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void nodeSelectListener(OrganigramNodeSelectEvent event) {
        FacesMessage message = new FacesMessage();
        // message.setSummary("Node '" + event.getOrganigramNode().getData() + "' selected.");

        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getData().toString());
        if (nf == null) {
            System.out.println("not division");
        } else {
            List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);

            if (ls != null) {
                for (Integer i : ls) {
                    Salarie s = salarieService.getSalarie(i);
                    Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, s);
                    if (org.isIsChef()) {
                        message.setSummary("Le Niveau : '" + event.getOrganigramNode().getData() + "' est Chef d'équipe");
                    }
                }
            }
        }

        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) {
        FacesMessage message = new FacesMessage();
        //   message.setSummary("Node '" + event.getOrganigramNode().getData() + "' collapsed.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void nodeExpandListener(OrganigramNodeExpandEvent event) {
        FacesMessage message = new FacesMessage();
        // message.setSummary("Node '" + event.getOrganigramNode().getData() + "' expanded.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void defineChef() {

        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getData().toString());
        List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);

        for (Integer i : ls) {
            Salarie s = salarieService.getSalarie(i);
//            System.out.println("SALARIE : " + s.getNom());
            Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, s);
            org.setIsChef(true);
            organigrameService.update(org);
        }

        List<Organigrame> orgsC = organigrameService.findByChantierChef(chantier);
/*
        for (Organigrame org : orgsC) {
            System.out.println("CHEF : " + org.getSalarie().getNom());
        }
*/
        FacesMessage message = new FacesMessage();
        message.setSummary("Vous avez défini ce niveau comme chef d'équipe");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void retirerChef() {

        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getData().toString());
        List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);

        for (Integer i : ls) {
            Salarie s = salarieService.getSalarie(i);
            Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, s);
            org.setIsChef(false);
            organigrameService.update(org);
        }

        FacesMessage message = new FacesMessage();
        message.setSummary("Ce Niveau n'est plus chef d'équipe");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void removeSalarie() {
        Salarie sa = null;
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        Chantier chantier = chantierService.findById(idChantier);
        NiveauFonction nf = niveauFonctionService.getByString(selection.getParent().getData().toString());

        List<Integer> ls = organigrameService.findByChantierNiveau(chantier, nf);
        for (Integer i : ls) {
            Salarie s = salarieService.getSalarie(i);
            String ss = selection.getData().toString();
            //System.out.println("NOM FROM FONCTION : " + ss.substring(0, ss.indexOf(' ')));

            if (s.getNom().equals(ss.substring(0, ss.indexOf(' ')))) {
                sa = s;
            }
        }
        // OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        //System.out.println("SELECTION : " + selection.getData().toString());
        //Salarie salarie = salarieService.getSalarie("", "", "", selection.getData().toString(), "");
        if (sa != null) {
            Organigrame org = organigrameService.findByChantierNiveauSalarie(chantier, nf, sa);
            if (org != null) {
                organigrameService.delete(org);
            }
//            System.out.println("REMOVING SALARIE");
        }

        initOrganigram(chantier);
        //  currentSelection.getParent().getChildren().remove(currentSelection);
    }

    public void addSalarie() {
        Chantier chantier = chantierService.findById(idChantier);
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
//        System.out.println("SELETCED NODE : " + selection.getData().toString());
        NiveauFonction nf = niveauFonctionService.getByString(selection.getData().toString());
//       OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
//        organigrameService.deleteOrgsByChantierNiveau(chantier, nf);

        for (Salarie s : selectedSalaries) {
//            System.out.println("SALARIE I DETECTED!");
//            OrganigramNode employee = new DefaultOrganigramNode("employee", s.getNom(), currentSelection);
//            employee.setDraggable(true);
//            employee.setSelectable(true);
            Organigrame org = new Organigrame();
            org.setChantier(chantier);
            org.setIsChef(false);
            org.setNiveau(nf);
            org.setSalarie(s);
            organigrameService.save(org);
            initOrganigram(chantier);
        }

    }

}
