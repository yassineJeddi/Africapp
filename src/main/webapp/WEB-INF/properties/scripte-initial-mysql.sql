USE stockbd;





-- alimentation table utilisateur ------------------

INSERT INTO `stockbd`.`utilisateur`
(`CIN`,
`EMAIL`,
`LOGIN`,
`MOBILE`,
`NOM`,
`PASSWORD`,
`PRENOM`)

     VALUES
           ('bk123456'
           ,'tgcc@tgcc.com'
           ,'admin'
           ,'060123654'
           ,'adminNom'
           ,'21232f297a57a5a743894a0e4a801fc3'
           ,'adminPrenom');
           



INSERT INTO `stockbd`.`utilisateur`
(`CIN`,
`EMAIL`,
`LOGIN`,
`MOBILE`,
`NOM`,
`PASSWORD`,
`PRENOM`)

     VALUES
           ('bk123456'
           ,'tgcc@tgcc.com'
           ,'adminchantier'
           ,'060123654'
           ,'adminchantierNom'
           ,'e51381d05d64870cfc10f0eaa8586807'
           ,'adminchantierPrenom');



INSERT INTO `stockbd`.`utilisateur`
(`CIN`,
`EMAIL`,
`LOGIN`,
`MOBILE`,
`NOM`,
`PASSWORD`,
`PRENOM`)

     VALUES
            ('bk123456'
           ,'tgcc@tgcc.com'
           ,'mensuel'
           ,'060123654'
           ,'mensuelNom'
           ,'7e7565d35a1d911430609db01a440166'
           ,'mensuelPrenom');

-- alimentation table permission ----------------

INSERT INTO `stockbd`.`permission`
(`PERMISSION`)

VALUES
           ('admin');



INSERT INTO `stockbd`.`permission`
(`PERMISSION`)


     VALUES
           ('pointage_des_engins');


INSERT INTO `stockbd`.`permission`
(`PERMISSION`)


     VALUES
           ('pointage_declaratif');


-- alimentation groupe ------------


INSERT INTO `stockbd`.`groupe`
(
`GROUPE`)
     VALUES
           ('admin');



INSERT INTO `stockbd`.`groupe`
(
`GROUPE`)
     VALUES
           ('adminchantier');


INSERT INTO `stockbd`.`groupe`
(
`GROUPE`)
     VALUES
           ('mensuel');


-- alimentation table crenau 


INSERT INTO `stockbd`.`crenau`
(`ID`,
`HOUR_FROM`,
`HOUR_TO`)
     VALUES
           (1 , 8 ,10);


INSERT INTO `stockbd`.`crenau`
(`ID`,
`HOUR_FROM`,
`HOUR_TO`)
     VALUES
           (2 ,10 ,12);

INSERT INTO `stockbd`.`crenau`
(`ID`,
`HOUR_FROM`,
`HOUR_TO`)
     VALUES
           (3 , 14 , 16);

INSERT INTO `stockbd`.`crenau`
(`ID`,
`HOUR_FROM`,
`HOUR_TO`)
     VALUES
           (4 ,16 ,18);


-- alimentation typologie consommation 


INSERT INTO `stockbd`.`typologieconsommation`
(`TYP_LIB`)
     VALUES
           ('UTILISE');




INSERT INTO `stockbd`.`typologieconsommation`
(`TYP_LIB`)
     VALUES
           ('CONSOMME');



-- alimentation status-transfert 


INSERT INTO `stockbd`.`statustransfert`
(`STAT_LIB`)
     VALUES
           ('REC');



INSERT INTO `stockbd`.`statustransfert`
(`STAT_LIB`)
     VALUES
           ('N-REC');

INSERT INTO `stockbd`.`statustransfert`
(`STAT_LIB`)
     VALUES
           ('RETR');

INSERT INTO `stockbd`.`statustransfert`
(`STAT_LIB`)
     VALUES
           ('ANNULE');



-- alimentation table sousFamille

INSERT INTO `stockbd`.`sousfamillestock`
(
`LIB_SFAMILLE`)
     VALUES
           ('FAM 01');



-- alimentation table famille stock --------------

INSERT INTO `stockbd`.`famillestock`
(
`LIB_FAMILLE`)
     VALUES
           ('SOUS FAM 01');





