package ma.bservices.webservice;

public class Commande {

    String resultat;
    Piece piece;
    Mouvement mouvements[];
    String nombreArticlesCommandes;

    /**
     * @return the piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @param piece the piece to set
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * @return the mouvements
     */
    public Mouvement[] getMouvements() {
        return mouvements;
    }

    /**
     * @param mouvements the mouvements to set
     */
    public void setMouvements(Mouvement[] mouvements) {
        this.mouvements = mouvements;
    }

    //Inner class
    /**
     * @return the resultat
     */
    public String getResultat() {
        return resultat;
    }

    /**
     * @param resultat the resultat to set
     */
    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    /**
     * @return the nombreArticleCommandes
     */
    public String getNombreArticlesCommandes() {
        return nombreArticlesCommandes;
    }

    /**
     * @param nombreArticleCommandes the nombreArticleCommandes to set
     */
    public void setNombreArticlesCommandes(String nombreArticlesCommandes) {
        this.nombreArticlesCommandes = nombreArticlesCommandes;
    }

    public class Piece {

        Entete entete;

        /**
         * @return the entete
         */
        public Entete getEntete() {
            return entete;
        }

        /**
         * @param entete the entete to set
         */
        public void setEntete(Entete entete) {
            this.entete = entete;
        }

    }

    public class Entete {

        String code_Fou;
        String nom_fou;
        String date;
        String ref_ent;
        String pino;
        String codchantier;
        String chantier;

        /**
         * @return the code_Fou
         */
        public String getCode_Fou() {
            return code_Fou;
        }

        /**
         * @param code_Fou the code_Fou to set
         */
        public void setCode_fou(String code_Fou) {
            this.code_Fou = code_Fou;
        }

        /**
         * @return the nom_fou
         */
        public String getNom_fou() {
            return nom_fou;
        }

        /**
         * @param nom_Fou the nom_Fou to set
         */
        public void setNom_fou(String nom_fou) {
            this.nom_fou = nom_fou;
        }

        /**
         * @return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * @return the ref_ent
         */
        public String getRef_ent() {
            return ref_ent;
        }

        /**
         * @param ref_ent the ref_ent to set
         */
        public void setRef_ent(String ref_ent) {
            this.ref_ent = ref_ent;
        }

        /**
         * @return the piNo
         */
        public String getPino() {
            return pino;
        }

        /**
         * @param pino the piNo to set
         */
        public void setPino(String pino) {
            this.pino = pino;
        }

        /**
         * @return the codChantier
         */
        public String getCodchantier() {
            return codchantier;
        }

        /**
         * @param codchantier the codChantier to set
         */
        public void setCodchantier(String codchantier) {
            this.codchantier = codchantier;
        }

        /**
         * @return the chantier
         */
        public String getChantier() {
            return chantier;
        }

        /**
         * @param chantier the chantier to set
         */
        public void setChantier(String chantier) {
            this.chantier = chantier;
        }

    }

    public class Mouvement {

        String ref;
        String quantite;
        String libelle;
        String montant;
        String enrNo;
        String piCod;
        String qteinitial;
        String qtevalidee;

        /**
         * @return the ref
         */
        public String getRef() {
            return ref;
        }

        /**
         * @param ref the ref to set
         */
        public void setRef(String ref) {
            this.ref = ref;
        }

        /**
         * @return the quantite
         */
        public String getQuantite() {
            return quantite;
        }

        /**
         * @param quantite the quantite to set
         */
        public void setQuantite(String quantite) {
            this.quantite = quantite;
        }

        /**
         * @return the libelle
         */
        public String getLibelle() {
            return libelle;
        }

        /**
         * @param libelle the libelle to set
         */
        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        /**
         * @return the montant
         */
        public String getMontant() {
            return montant;
        }

        /**
         * @param montant the montant to set
         */
        public void setMontant(String montant) {
            this.montant = montant;
        }

        /**
         * @return the enrNo
         */
        public String getEnrNo() {
            return enrNo;
        }

        /**
         * @param enrNo the enrNo to set
         */
        public void setEnrNo(String enrNo) {
            this.enrNo = enrNo;
        }

        /**
         * @return the piCod
         */
        public String getPiCod() {
            return piCod;
        }

        /**
         * @param piCod the piCod to set
         */
        public void setPiCod(String piCod) {
            this.piCod = piCod;
        }

        /**
         * @return the qteInitial
         */
        public String getQteinitial() {
            return qteinitial;
        }

        /**
         * @param qteinitial the qteInitial to set
         */
        public void setQteinitial(String qteinitial) {
            this.qteinitial = qteinitial;
        }

        /**
         * @return the qteValidee
         */
        public String getQtevalidee() {
            return qtevalidee;
        }

        /**
         * @param qtevalidee the qteValidee to set
         */
        public void setQtevalidee(String qtevalidee) {
            this.qtevalidee = qtevalidee;
        }

    }

}
