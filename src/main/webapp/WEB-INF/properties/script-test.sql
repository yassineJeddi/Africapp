USE [enginsTest]

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



delete from SALARIE 

GO


DBCC CHECKIDENT (SALARIE, reseed, 0)

GO


delete from CHANTIER

GO

DBCC CHECKIDENT (CHANTIER, reseed, 0)




-- alimentation table salarie ------------------

INSERT INTO [enginsTest].[dbo].[SALARIE]
           ([DTYPE]
           ,[ADRESSE]
           ,[ADRESSEARABE]
           ,[AFFECTATION]
           ,[AFFECTEPAR]
           ,[CIN]
           ,[CNSS]
           ,[CREEPAR]
           ,[DATECREATION]
           ,[DATEMODIFICATION]
           ,[DATENAISSANCE]
           ,[EMAIL]
           ,[GSM]
           ,[LIEUNAISSANCE]
           ,[MATRICULE]
           ,[MATRICULEDIVALTO]
           ,[MODIFIEPAR]
           ,[NOM]
           ,[NOMAGENCE]
           ,[NOMARABE]
           ,[NOMBANQUE]
           ,[NOMBREENFANTS]
           ,[PRENOM]
           ,[PRENOMARABE]
           ,[RIB]
           ,[Statut]
           ,[TELEPHONE]
           ,[VILLE])
     VALUES
           ('mensuel' ,'casa','casa arabe',1,'admin','Bk236547','12lkam','admin','12-01-2008','12-01-2009','12-01-1991'
           ,'said@tgcc.com','0601236547' ,'casa','122' ,'123','admin','said','bmci','said arabe','bmci'
           ,3,'said','said arabe','123654789','actif','060111223366','casa')
GO


-- alimentation chantier -------------------


INSERT INTO [enginsTest].[dbo].[CHANTIER]
           ([DATEDebut]
           ,[adresse]
           ,[AFFAIRE]
           ,[DOS]
           ,[HEUREENTREE]
           ,[HEURESORTIE]
           ,[LIB80]
           ,[NOMBREHEURES]
           ,[NOMBREMINUTES]
           ,[UP_REGION]
           ,[ville])
     VALUES
           ('12-01-2008', 'casa'   , 'affaire' , 1  ,'08:00' ,'17 : 00 ' ,'chantierLib',5,155,'casa','casa')
GO





