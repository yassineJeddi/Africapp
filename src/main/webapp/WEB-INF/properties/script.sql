USE [enginsTest]

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
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

GO

DBCC CHECKIDENT (UTILISATEUR, reseed, 0)

GO

DBCC CHECKIDENT (Groupe, reseed, 0)

GO

DBCC CHECKIDENT ([PERMISSION], reseed, 0)
GO

DBCC CHECKIDENT (CRENAU , reseed, 0)

GO






-- alimentation table utilisateur ------------------


INSERT INTO [enginsTest].[dbo].[UTILISATEUR]
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


INSERT INTO [enginsTest].[dbo].[UTILISATEUR]
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


INSERT INTO [enginsTest].[dbo].[UTILISATEUR]
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

INSERT INTO [enginsTest].[dbo].[PERMISSION]
           ([PERMISSION])
     VALUES
           ('admin')
GO


INSERT INTO [enginsTest].[dbo].[PERMISSION]
           ([PERMISSION] )
     VALUES
           ('pointage_des_engins')
GO

INSERT INTO [enginsTest].[dbo].[PERMISSION]
           ([PERMISSION])
     VALUES
           ('pointage_declaratif')
GO

-- alimentation groupe ------------


INSERT INTO [enginsTest].[dbo].[GROUPE]
           ([GROUPE])
     VALUES
           ('admin')
Go


INSERT INTO [enginsTest].[dbo].[GROUPE]
           ([GROUPE])
     VALUES
           ('adminchantier')
Go

INSERT INTO [enginsTest].[dbo].[GROUPE]
           ([GROUPE])
     VALUES
           ('mensuel')
Go

-- alimentatione table groupe-utilisateur -----------

INSERT INTO [enginsTest].[dbo].[USER_GROUP]
           ([ID_USER]
           ,[ID_GROUP])
     VALUES
           (1,1)
GO 

INSERT INTO [enginsTest].[dbo].[USER_GROUP]
           ([ID_USER]
           ,[ID_GROUP])
     VALUES
           (2 , 2 )
GO 

INSERT INTO [enginsTest].[dbo].[USER_GROUP]
           ([ID_USER]
           ,[ID_GROUP])
     VALUES
           (3 , 3)
GO 

-- alimentation table permision groupe ----------

INSERT INTO [enginsTest].[dbo].[GROUP_PERMISSION]
           ([ID_GROUP]
           ,[ID_PERMISSION])
     VALUES
           (1 , 1)
GO

INSERT INTO [enginsTest].[dbo].[GROUP_PERMISSION]
           ([ID_GROUP]
           ,[ID_PERMISSION])
     VALUES
           (2 , 2)
GO

INSERT INTO [enginsTest].[dbo].[GROUP_PERMISSION]
           ([ID_GROUP]
           ,[ID_PERMISSION])
     VALUES
           (3 , 3)
GO


-- alimentation table crenau 


INSERT INTO [enginsTest].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (1 , 8 ,10)
GO

INSERT INTO [enginsTest].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (2 ,10 ,12)
GO
INSERT INTO [enginsTest].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (3 , 14 , 16)
GO
INSERT INTO [enginsTest].[dbo].[CRENAU]
           ([ID]
           ,[HOUR_FROM]
           ,[HOUR_TO])
     VALUES
           (4 ,16 ,18)
GO





