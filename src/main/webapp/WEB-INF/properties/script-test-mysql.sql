USE enginsTest;




delete from SALARIE where ID>0;

ALTER TABLE SALARIE AUTO_INCREMENT = 1;




delete from CHANTIER where PRJAP_ID>0;

ALTER TABLE CHANTIER AUTO_INCREMENT = 1;




-- alimentation table salarie ------------------

INSERT INTO `enginstest`.`salarie`
(`DTYPE`,
`ADRESSE`,
`ADRESSEARABE`,
`AFFECTATION`,
`AFFECTEPAR`,
`CIN`,
`CNSS`,
`CREEPAR`,
`DATECREATION`,
`DATEMODIFICATION`,
`DATENAISSANCE`,
`EMAIL`,
`GSM`,
`LIEUNAISSANCE`,
`MATRICULE`,
`MATRICULEDIVALTO`,
`MODIFIEPAR`,
`NOM`,
`NOMAGENCE`,
`NOMARABE`,
`NOMBANQUE`,
`NOMBREENFANTS`,
`PRENOM`,
`PRENOMARABE`,
`RIB`,
`Statut`,
`TELEPHONE`,
`VILLE`)
     VALUES
           ('mensuel' ,'casa','casa arabe',1,'admin','Bk236547','12lkam','admin','12-01-2008','12-01-2009','12-01-1991'
           ,'said@tgcc.com','0601236547' ,'casa','122' ,'123','admin','said','bmci','said arabe','bmci'
           ,3,'said','said arabe','123654789','actif','060111223366','casa');



-- alimentation chantier -------------------


INSERT INTO `enginstest`.`chantier`
(`DATEDebut`,
`adresse`,
`AFFAIRE`,
`DOS`,
`HEUREENTREE`,
`HEURESORTIE`,
`LIB80`,
`NOMBREHEURES`,
`NOMBREMINUTES`,
`UP_REGION`,
`ville`)
     VALUES
           ('2008-01-17', 'casa'   , 'affaire' , 1  ,'08:00' ,'17 : 00 ' ,'chantierLib',5,155,'casa','casa');







