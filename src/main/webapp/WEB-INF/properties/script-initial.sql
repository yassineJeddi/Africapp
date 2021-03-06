USE stockdb

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



delete from SousFamilleStock
GO

delete from FamilleStock
GO



delete from USER_GROUP

GO

delete  from GROUP_PERMISSION

GO

delete from UTILISATEUR
Go

delete from Groupe
Go

delete from [PERMISSION]
Go

delete from USER_GROUP

GO

delete  from GROUP_PERMISSION

GO

delete from CRENAU 


Go

delete from TypologieConsommation 

GO


delete from StatusTransfert 

GO

DBCC CHECKIDENT (StatusTransfert, reseed, 0)

GO

DBCC CHECKIDENT (TypologieConsommation, reseed, 0)

GO

DBCC CHECKIDENT (UTILISATEUR, reseed, 0)

GO

DBCC CHECKIDENT (Groupe, reseed, 0)

GO

DBCC CHECKIDENT ([PERMISSION], reseed, 0)
GO


DBCC CHECKIDENT (SousFamilleStock, reseed, 0)

GO

DBCC CHECKIDENT (FamilleStock, reseed, 0)

GO



-- alimentation table utilisateur ------------------


INSERT INTO [stockdb].[dbo].[UTILISATEUR]
           ([CIN]
           ,[EMAIL]
           ,[LOGIN]
           ,[MOBILE]
           ,[NOM]
           ,[PASSWORD]
           ,[PRENOM])
     VALUES
           ('bk123456'
           ,'tgcc@tgcc.com'
           ,'admin'
           ,'060123654'
           ,'adminNom'
           ,'21232f297a57a5a743894a0e4a801fc3'
           ,'adminPrenom')
           
GO


INSERT INTO [stockdb].[dbo].[UTILISATEUR]
           ([CIN]
           ,[EMAIL]
           ,[LOGIN]
           ,[MOBILE]
           ,[NOM]
           ,[PASSWORD]
           ,[PRENOM])
     VALUES
           ('bk123456'
           ,'tgcc@tgcc.com'
           ,'adminchantier'
           ,'060123654'
           ,'adminchantierNom'
           ,'e51381d05d64870cfc10f0eaa8586807'
           ,'adminchantierPrenom')
GO


INSERT INTO [stockdb].[dbo].[UTILISATEUR]
           ([CIN]
           ,[EMAIL]
           ,[LOGIN]
           ,[MOBILE]
           ,[NOM]
           ,[PASSWORD]
           ,[PRENOM])
     VALUES
            ('bk123456'
           ,'tgcc@tgcc.com'
           ,'mensuel'
           ,'060123654'
           ,'mensuelNom'
           ,'7e7565d35a1d911430609db01a440166'
           ,'mensuelPrenom')
GO

-- alimentation table permission ----------------

INSERT INTO [stockdb].[dbo].[PERMISSION]
           ([PERMISSION])
     VALUES
           ('admin')
GO


INSERT INTO [stockdb].[dbo].[PERMISSION]
           ([PERMISSION] )
     VALUES
           ('pointage_des_engins')
GO

INSERT INTO [stockdb].[dbo].[PERMISSION]
           ([PERMISSION])
     VALUES
           ('pointage_declaratif')
GO

-- alimentation groupe ------------


INSERT INTO [stockdb].[dbo].[GROUPE]
           ([GROUPE])
     VALUES
           ('admin')
Go


INSERT INTO [stockdb].[dbo].[GROUPE]
           ([GROUPE])
     VALUES
           ('adminchantier')
Go

INSERT INTO [stockdb].[dbo].[GROUPE]
           ([GROUPE])
     VALUES
           ('mensuel')
Go

-- alimentatione table groupe-utilisateur -----------

INSERT INTO [stockdb].[dbo].[USER_GROUP]
           ([ID_USER]
           ,[ID_GROUP])
     VALUES
           (1,1)
GO 

INSERT INTO [stockdb].[dbo].[USER_GROUP]
           ([ID_USER]
           ,[ID_GROUP])
     VALUES
           (2 , 2 )
GO 

INSERT INTO [stockdb].[dbo].[USER_GROUP]
           ([ID_USER]
           ,[ID_GROUP])
     VALUES
           (3 , 3)
GO 

-- alimentation table permision groupe ----------

INSERT INTO [stockdb].[dbo].[GROUP_PERMISSION]
           ([ID_GROUP]
           ,[ID_PERMISSION])
     VALUES
           (1 , 1)
GO

INSERT INTO [stockdb].[dbo].[GROUP_PERMISSION]
           ([ID_GROUP]
           ,[ID_PERMISSION])
     VALUES
           (2 , 2)
GO

INSERT INTO [stockdb].[dbo].[GROUP_PERMISSION]
           ([ID_GROUP]
           ,[ID_PERMISSION])
     VALUES
           (3 , 3)
GO


-- alimentation table crenau 


INSERT INTO [stockdb].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (1 , 8 ,10)
GO

INSERT INTO [stockdb].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (2 ,10 ,12)
GO
INSERT INTO [stockdb].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (3 , 14 , 16)
GO
INSERT INTO [stockdb].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (4 ,16 ,18)
GO

-- alimentation typologie consommation 


INSERT INTO [stockdb].[dbo].[TypologieConsommation]
           ([TYP_LIB])
     VALUES
           ('utilisé')
GO


INSERT INTO [stockdb].[dbo].[TypologieConsommation]
           ([TYP_LIB])
     VALUES
           ('consommé')
GO


-- alimentation status-transfert 


INSERT INTO [stockdb].[dbo].[StatusTransfert]
           ([STAT_LIB])
     VALUES
           ('receptionné')
GO


INSERT INTO [stockdb].[dbo].[StatusTransfert]
           ([STAT_LIB])
     VALUES
           ('non receptionné')
GO


-- alimentation table sousFamille


INSERT INTO [stockdb].[dbo].[SousFamilleStock]
           ([LIB_SFAMILLE])
     VALUES
           ('lib famille')
GO


-- alimentation table famille stock --------------

INSERT INTO [stockdb].[dbo].[FamilleStock]
           ([LIB_FAMILLE])
     VALUES
           ('lib famille')
GO




