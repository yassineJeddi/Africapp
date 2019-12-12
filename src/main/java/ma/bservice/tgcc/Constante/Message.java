/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservice.tgcc.Constante;

/**
 *
 * @author j.allali
 */
public class Message {

    /**
     * ChantierMb
     */
    public static final String CHANTIER_ZONE_SUCCESS = "La zone a été ajoutée avec succès pour le chantier :";
    public static final String CHANTIER_ZONE_FALSE = "L'opération d'ajouter la zone a échoué pour le chantier :";

    public static final String ONROWEDIT_CHANTIER_SUCCESS = "La modification est faite avec succès pour le chantier :";
    public static final String ONROWEDIT_CHANTIER_FALSE = "La Modifiation a échoué pour le chantier :";

    public static final String ONROWCANCEL_CHANTIER_TRUE = "La Modifiation est annulée pour le chantier :";

    public static final String DELETE_CHANTIER_TRUE = "Le Chantier a été supprimé avec succès";
    public static final String DELETE_CHANTIER_FALSE = "L'opération de supprimer ce chantier a été échoué";

    public static final String CHANTIER_UPLOAD = "La liste des chantiers a été importée avec succès!";

    /**
     * fonctionServMb
     */
    public static final String FONCTION_UPLOAD = "La liste des fonctions a été importée avec succès!";

    /**
     * CiterneMb
     */
    public static final String CHARGE_DOCUMERNT = "Il faut charger le document";
    public static final String BON_LIVRAISON_TRUE = "Le bon Livraison Citerne est Ajouté avec succès";
    public static final String NUM_COMMANDE = "Le numéro commande existe déjà ";
    public static final String VOLUME_CAPACITE_CITERNE_DEPASSE = "Le volume Dépasse la capacité de citerne ";
    public static final String VOLUME_CAPACITE_CITERNE_INF = "le nombre de litre  est supérieure volume de citerne ";

    public static final String VOITURE_EXIST_MENSUEL = "n'a aucune voiture à ce mensuel";

    public static final String CITERNE_UPLOAD = "La liste des citernes a été importée avec succès!";

    /**
     * EnginPanneMb
     */
    public static final String ENGIN_PANNE = "Cet engin a bien été mis en panne";
    // public static final String DECLARATION_PANNE = "La panne "
    /**
     * Gestion_CiterneMb
     */
    public static final String AOUT_CITERNE = "la citerne est ajoutée avec succès ";
    public static final String LIBELLE_CITERNE_EXIST = "le nom de citerne est existe déjà ";
    public static final String CITERNE_CONTROL = "il faut saisir les champ obligatoires (*) ";

    public static final String DELETE_CITERNE = "cette citerne est supprimée avec succès ";
    public static final String UPDATE_CITERNE_CANCEL = "Modification de la citerne est annulée ";
    public static final String UPDATE_CITERNE_SUCCESS = "La citerne est modifiée avec succès ";

    public static final String VOLUMEDEPPASE0_ERREUR = "le modification n'est pas possible. Il y a déjà eu des livraisons sur cette citerne.";

    public static final String CAPACITE_INFERIURE_VOLUME_ACTUEL = "capacite de citerne est inferieur au volume actuel";

    /**
     * GroupeMb
     */
    public static final String ADD_GROUPE = "Groupe(s) ont été ajoutés : ";
    public static final String ADD_GROUPE_ERROR = "Erreur dans l'ajout des données ";
    public static final String DELETE_GROUPE = "Groupe est supprimé avec succée ";
    public static final String DELETE_GROUPE_ERROR = "la suppression du groupe a échoué !";
    public static final String UPDATE_GROUPE = "Groupe est modifié avec succès ";

    /**
     * LotMb
     */
    public static final String UPDATE_LOT = "Lot a été modifié avec succès";
    public static final String UPDATE_LOT_CANCEL = "Annulation de la modification";
    public static final String DELETE_LOT = "Lot est supprimé avec succès ";
    public static final String ADD_LOT = "Lot est ajouté avec succès ";
    /**
     * ParcEnginMb
     */
    public static final String ARCHIVE_ENGIN = "Cet engin est archivé avec succès";
    public static final String REINTEGRE_ENGIN = "Cet engin a été réintégrer ";
    public static final String REFORME_ENGIN = "Cet engin est réformé ";
    public static final String UPDATE_ENGIN = "Cet engin est modifié avec succès";
    public static final String ADD_ENGIN = "Cet engin est ajouté avec succès";
    public static final String AFFECT_ENGIN = "Cet engin est affecté avec succès";
    /**
     * PermissionMb
     */
    public static final String ADD_PERMISSION = "Permission(s) ont été ajoutés : ";
    public static final String DELETE_PERMISSION_SUCCESS = "Permission est supprimé avec succès ";
    public static final String DELETE_PERMISSION_ERROR = "La suppression de la permission a échoué ";
    public static final String UPDATE_PERMISSION_SUCCESS = "Permission est modifié avec succée ";
    public static final String UPDATE_PERMISSION_CANCEL = "La modification de cette permission est annulée";
    /**
     * PointageEnginMb
     */
    public static final String AFFECT_ENGIN_CHANTIER = "Aucun engin sur ce chantier";
    public static final String POINTAGE_DEJA_AFFECTED = "Le pointage est déjà effectué pour ce jour";
    public static final String NO_ENGIN_SELECTED = "Aucun engin n'a été séléctionné";
    public static final String ERROR_DONNEE_PE = "Erreur dans les données du pointage";
    public static final String ADD_PE_BYDATE = " Pointage(s) ont été ajoutés pour la date : ";
    /**
     * ZoneMb
     */
    public static final String ADD_ZONE_SUCCESS = "Cette Zone a été ajouté avec succès ";
    public static final String ADD_ZONE_FALSE = "L'ajout de cette zone a été échoué";
    public static final String DELETE_ZONE_SUCCESS = "La zone a été supprimer avec succès ";
    public static final String DELETE_ZONE_FALSE = "L'opération de supprimer la zone a été echoué";
    public static final String UPDATE_ZONE_SUCCESS = "Cette zone a été modifié avec succès :";
    public static final String UPDATE_ZONE_FALSE = "L'opération de modifier cette zone a été échoué : ";
    public static final String UPDATE_ZONE_CANCEL = "Annulation de la modification";
    /**
     * ArticleMb
     */
    public static final String AFFECT_ARTICLE = "Cet article est affecté avec succès!";
    public static final String TRANSFERT_ARTICLE = "Cet article est Transferé avec succès!";
    /**
     * OperationMb
     */
    public static final String AFFECT_SUCCESS = "Parfait! affectation effectuée avec succès";
    public static final String STRING_Article = "Affectation de l'article ";
    public static final String STRING_ZONE = " à la zone ";
    public static final String STRING_LOT = " et lot ";
    public static final String BON_GENERATED = " est effectuée avec succès, un bon de cette affectation a été generé";
    public static final String STRING_ATT = "Attention!";
    public static final String STRING_AJOUT_ARTICLE = "vous avez deja ajouté cet article!";
    public static final String STRING_DELETE_AFFECT = "vous nous pouvez plus annuler cette affectation";
    public static final String STRING_DELETE_AFFECT_S = "cette affectation à été annulée!";
    public static final String STRING_AJOUT = "Ajouté!";
    public static final String STRING_ARTICLE_TRANSFERT = "l'article à été ajouté au transfèrt";
    public static final String STRING_TRANSFERT_SUCCESS = "Succès! Transfer";
    public static final String TRANSFERT_ARTICLE_BON_GENE = "Transfert de l'article est effectué avec succès un bon de ce transfert a été généré! ";
    public static final String STRING_RECEPTION = "Succès! Réception";
    public static final String STRING_SUCCESS_RE = "Succès! Consommation";
    public static final String STRING_CONSOMMATION_SUCCESS = "Consommation de l'article est effectuée avec succès";
    public static final String STRING_RETOUR = "Succès! Retour";
    public static final String RETOUR_ARTICLE_SUCCESS = "Retour de l'article est effectué avec succès";
    public static final String UPDATE_AFFECTATION = "Affectation Modifiée";
    public static final String AFFECTATION_CANCEL = "Affectaion annulée";
    public static final String UPDATE_CONSOMMATION = "Consommaion Modifiée";
    public static final String CONSOMMATION_CANCEL = "Consommation annulée";
    public static final String RETOUR_ERR_CANCEL = "Vous ne pouvez plus annuler cette opération!";
    public static final String UPDATE_BACK = "Retour Modifié";
    public static final String BACK_CANCEL = "Retour annulé";
    public static final String TRANSFET_SELECTED = "Transfer Selected";
    public static final String TRANSFERT_UNSELECTED = "Transfer Unselected";
    /**
     * AbsenceMb
     */
    public static final String UPDATE_ABSENCE = "Absence est modifié avec succès ";
    /**
     * Carte_GasoilSalarieMb
     */
    public static final String AFFECT_CARTE_GASOIL = "Carte Gasoile est afféctée avec succès ";
    public static final String AFFECT_CARTE_GASOIL_CANCEL = "L'opération est annulée";
    public static final String CARTE_GASOIL_DESAFFECTER = "Carte Gasoil  est désaffecté avec succès";

    public static final String DESAFFECT_CARTE_GASOIL = "Affectation de carte gasoil est annulé avec sucès! ";
    public static final String AFFECT_CARTE_GASOIL_WARNING = "Veuillez sélectioner un mensuel";
    public static final String CHOISIR_MENSUEL_NONVOITURE_AFFECTE_WARNING = "Ce mensuel n'a pas de voiture!";

    /**
     * Carte Gasoil chantier mb
     */
    public static final String CHOISIR_CHANTIER_NONVOITURE_AFFECTE_WARNING = "Veuillez affecter une voiture à ce chantier!";

    /**
     * DocumentMb
     */
    public static final String STRING_CHARGE_DOCUMENT = "Il faut charger le document";
    public static final String STRING_CHARGE_DOCUMENT_PDF = "Il faut charger le document en format PDF ";
    public static final String STRING_CHARGE_DOCUMENT_DONE = "Document est Ajouté Avec Succès";
    public static final String DELETE_DOCUMENT = "Le document est supprimé avec succès ";
    public static final String ADD_DOCUMENT = "Le document est ajouté avec succès ";
    public static final String UPDATE_TITRE = "Le document est modifiè avec succès ";
    public static final String UPDATE_TITRE_CANCEL = "La modification du document est annulé ";
    /**
     * GestionCarteGasoilMb
     */
    public static final String NUMS_CARTE_GASOIL_EXIST = "numero carte gasoil est déjà exist";

    public static final String ADD_CARTE_GASOIL = "Carte Gasoil est ajoutee avec succès ";
    public static final String DELETE_CARTE_GASOIL_FALSE = " Cette Carte Gasoil est déjà  affectée ; impossible de la supprimé ";
    public static final String DELETE_CARTE_GASOIL_SUCCESS = "Carte Gasoil est supprimée avec succès ";
    public static final String UPDATE_CARTE_GASOIL_SUCCESS = "Carte Gasoil est modifiée avec succès ";
    public static final String UPDATE_CARTE_GASOIL_CANCEL = "la modification de la Carte Gasoil est annulée ";

    /**
     * GestionModem3G
     */
    public static final String ADD_3G_SUCCESS = "Le modem 3g est ajouté avec succès ";
    public static final String DELETE_3G_FALSE = " Ce modem 3g est déjà affécté; impossible de le Supprimé ";
    public static final String DELETE_3G_SUCCESS = "Le modem 3g est supprimé avec succès ";
    public static final String UPDATE_3G_SUCCESS = "Le modem 3g est modifié avec succès ";
    public static final String UPDATE_3G_CANCEL = "La modification modem 3g est annulée ";

    public static final String IMEI_3G_EXIST = "imei est déjà exist ";

    public static final String NUMS_3G_EXIST = "numero serie est déjà exist ";

    public static final String NUMT_3G_EXIST = "numero 3G est déjà exist ";

    /**
     * Gestion Ordinateur Mb
     */
    public static final String ADD_ORDINATEUR_SUCCESS = "Ordinateur est ajouté avec succès ";
    public static final String DELETE_ORDINATEUR_FALSE = " Ordinateur est déjà affécté impossible de le Supprimé ";
    public static final String DELETE_ORDINATEUR_SUCCESS = "Ordinateur est supprimé avec succès ";
    public static final String UPDATE_ORDINATEUR_SUCCESS = "Ordinateur est modifié avec succès ";
    public static final String UPDATE_ORDINATEUR_CANCEL = "La modification ordinateur est annulé ";
    public static final String NUMS_ORDINATEUR_EXIST = "numero Serie  est déjà exist ";

    /**
     * Telephone Mb
     */
    public static final String NUMS_TELEPHONE_EXIST = "numero serie est déjà exist  ";

    public static final String ADD_TELEPHONE_SUCCESS = "Téléphone est ajouté avec succès ";
    public static final String DELETE_TELEPHONE_FALSE = " Ce Téléphone est déjà affécté;  impossible de le Supprimé ";
    public static final String DELETE_TELEPHONE_SUCCESS = "le téléphone est supprimé avec succès ";
    public static final String UPDATE_TELEPHONE_SUCCESS = "le téléphone est modifié avec succès ";
    public static final String UPDATE_TELEPHONE_CANCEL = "La modification du téléphone est annulé ";
    /**
     * Gestion Voiture Mb
     */
    public static final String TITRE_OBLIGATOIRE = "le titre est obligatoire";
    public static final String FILE_OBLIGATOIRE = "fichier est obligatoire";
    public static final String NUMCHASSIS_EXIST = "Numero Chassis est déjà exist";
    public static final String MATRICULEVOITURE_EXIST = "Matricule est déjà exist";
    public static final String NUMCONTRATASSURANCE_EXIST = "Numero Contrat d'assurance est déjà exist";
    public static final String NUMCARTEGRISE_EXIST = "Numero Carte Grise est déjà exist";
    public static final String NUMMATRICULATIONPROVISOIRE_EXIST = "Immatriculation provisoire est déjà exist";
    public static final String ADD_VOITURE_SUCCESS = "La voiture est ajoutée avec succès ";
    public static final String AFFECTED_VOITURE_SUCCESS = "La voiture est affectée avec succès ";
    public static final String DELETE_VOITURE_FALSE = " Cette Voiture est déjà afféctée ; impossible de la Supprimé ";
    public static final String DELETE_VOITURE_SUCCESS = "La voiture est suprimée avec succès ";
    public static final String UPDATE_VOITURE_SUCCESS = "La voiture est modifiée avec succès ";
    public static final String DESAFCT_VOITURE_SUCCESS = "La voiture est désaffecté avec succès ";
    public static final String UPDATE_VOITURE_CANCEL = "La modification de la voiture est annulée ";
    public static final String AFFECTER_VOITURE_CHANTIER_WARNING = "Il faut choisir le chantier  ";
    public static final String AFFECTER_VOITUR_WARNING = "Il faut choisir une voiture  ";
    public static final String EXISTE_VOITUR_WARNING = "il y a déja une voite qui a de ces élément(WW, Matricule, Num Chassi, Num Carte grise, Num Assurance) ";
    
    /**
     * LoyerMb
     */
    public static final String ADD_BON_CAISSE_LOYER = "Le bon de caisse est Ajouté avec succès ";
    public static final String MISE_JOUR_CANCEL = "Mise à jour est annulé avec succès ";
    public static final String NO_BON_CAISSE_LOYER = "Le Loyer n'a aucun Bon de Caisse";
    public static final String AFFECT_LOYER_CHANTIER = "Loyer est affecté avec succès au chantier ";
    public static final String ADD_LOYER_SUCCESS = "Loyer est ajouté avec succès ";
    public static final String AFFECT_LOYER_SUCCESS = "Loyer est affecté avec succès ";
    public static final String ADD_LOYER_FAIL_RIB = "Le numéro de RIB est invalid";

    public static final String DELETE_RECUCAISSE_TRUE = "Le Reçu a été supprimé avec succès";

    public static final String NUMCONTRAT_EXIST = "Numero Contrat est déjà exist";
    public static final String VERIFICATION_NOM = "il faut saisir le nom de propriétaire";
    public static final String VERIFICATION_PRENOM = "il faut saisir le prénom de propriétaire";
    public static final String VERIFICATION_RIB = "il faut saisir le rib";
    public static final String CHANTIER_EXIST_LOYER = "il faut choisir le chantier ";
    public static final String MENSUEL_EXIST_LOYER = "il faut choisir le mensuel ";

    /**
     * MensuelMb
     */
    public static final String STRING_SUCCESS = "Succesful";
    public static final String STRING_UPLOAD = " is uploaded.";
    public static final String UPLOAD_SUCCESS = "Chargement Términé.";
    public static final String MENSUEL_UPLOAD = "La liste des mensuels a été importée avec succès!";
    /**
     * Modem3GMb
     */
    public static final String DEJA_AFFECT_3G = "Modem 3g est déjà affécté à ";
    public static final String AFFECT_3G_SUCCESS = "Modem 3g est affécté avec succès ";
    public static final String MENSUEL_NO_3G = "Ce mensuel n'a aucun modem 3g";
    public static final String DESAFFECTED_3G = "Modem 3g est désaffecté avec succès ";
    public static final String ANNULERAFFECTATION_3G = "l'affectation est annulé avec succès ";
    /**
     * OrdinateurMb
     */
    public static final String AFFECT_ORDINATEUR = "Ordinateur est Affecté avec succès ";
    public static final String MENSUEL_NO_ORDINATEUR = "Ce mensuel n'a aucun ordinateur";
    public static final String DESAFFECTED_ORDIANTEUR = "Ordinateur est désaffecté à avec succès ";
    public static final String DATE_RENDU_ORDINATEUR = "Date Rendu est ajoutée avec succès ";
    public static final String NO_ORDIANTEUR_BYMARQUE_ = "Tout les ordinateurs sont afféctés ";
    public static final String ANNULERAFFECATIONORDINATEUR = "l'affectation est annulé avec succès";
    /**
     * PointageMb
     */
    public static final String POINTAGE_NON_EFFECTUE = "Le pointage de cette semaine n'a pas été éffectué";
    public static final String POINTAGE_ = "On est pas encore arrivé à cette semaine";
    public static final String POIN_DECLARATIF_DATE = "Le pointage déclaratif a été pris en compte après cette date";
    public static final String MSG_SEMAINE_APOINTER = "Vous avez des semaines à pointer, A partir de la semaine : ";
    public static final String NO_CHANTIER_AFFECTED = "Vous n'êtes affecté à un aucun chantier";
    public static final String POINTAGE_EFFECTUE = "Le pointage de cette semaine a bien été effectué";
    public static final String VALIDATE_POINTAGE = "Vous ne pouvez pas valider définitivement le pointage avant la fin de la semaine.";
    public static final String STRING_VALIDATION_DEFE = "Validation définitive : ";
    public static final String ADD_POINTAGE_SUCCESS = "Les pointages de cette semaine ont bien été ajoutés.";
    public static final String STRING_VALIDATION = "Validation : ";
    public static final String ADD_UPDATE_POINTAGE = "Les pointages de cette semaine ont bien été ajoutés, vous pouvez a tout moment modifier vos pointages.";
    /**
     * TelephoneMb
     */
    public static final String TELEPHONE_AFFECTED_DEJA = "Ce téléphone est déjà affécté à ";
    public static final String TELEPHONE_AFFECTED = "Téléphone est affécté avec succès ";
    public static final String MENSUEL_NO_TELEPHONE = "Ce mensuel n'a aucun téléphone affecté";
    public static final String DESAFFECTED_TELEPHONE = "Affectation du téléphone est annulé avec succès!";
    public static final String DESAFFECTE_TELEPHONE = "téléphone est Désaffecté avec succès!";

    /**
     * VoitureChantierMb
     */
    public static final String VOITURE_CHANTIER_AFFECTED = "n'a aucune voiture affecté";
    public static final String DESAFFECTED_VOITURE = "Cette affectation a été annulée avec succès!";
    public static final String VOITURE_DESAFFECTER = "Voiture  est désaffecté avec succès";
    /**
     * Pointage Mensuel quiz Mb
     */
    public static final String MENSUEL_POINTE = "Mensuel est pointé avec succès ";

    /**
     * MISE EN PANNE
     */
    public static final String MISE_EN_PANNE_SUCCESS = "Le véhicule a été mis en panne";
    public static final String MISE_EN_PANNE_ERROR = "Erreur, impossible de mettre cet engin en panne";

    /**
     * affectationMb
     */
    public static final String AFFECT_FINAN = "L'affection est faite avec succès";

    /**
     * Loaders
     */
    public static final String CARTE_UPLOAD = "La liste des cartes gasoil a été importée avec succès!";
    public static final String ENGIN_UPLOAD = "La liste des engins a été importée avec succès!";
    public static final String MODEM_UPLOAD = "La liste des modem 3G a été importée avec succès!";
    public static final String TELEPHONE_UPLOAD = "La liste des téléphones a été importée avec succès!";
    public static final String VOITURE_UPLOAD = "La liste des voitures a été importée avec succès!";
    public static final String VOITURECH_UPLOAD = "La liste des voitures chantier a été importée avec succès!";
    public static final String VOITURESA_UPLOAD = "La liste des voitures salariés a été importée avec succès!";
    public static final String DOCUMENT_UPLOAD = "La liste des documents a été importée avec succès!";
    public static final String ORDINATEUR_UPLOAD = "La liste des ordinateurs a été importée avec succès!";
    public static final String TOUT_UPLOAD = "Toutes les données ont été importée avec succès!";

    /**
     * bon caisse
     */
    public static final String VERIFICATION_ANNEE_MOIS_BONCAISSE = " Un bon caisse existe pour ce mois et année, veuillez choisir une date différente ";

}
