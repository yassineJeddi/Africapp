/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import ma.bservices.tgcc.Entity.CarteGasoil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author airaamane
 */
public class ExcelParser {

    public static Map<String, List<String>> parseExcelFile(String sourceXls) {

        //liste des lots par article
        List<String> listOfValuesPerArticle = new LinkedList<>();

        // map des articles avec les lots correspondants
        Map<String, List<String>> mapOfArtLotFromXls = new HashMap<>();

        try {

            FileInputStream file = new FileInputStream(new File(sourceXls));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int rowInt = 1;
            int colInt = 1;

            //l'article figurant sur chaque ligne du fichier
            String articleOfRow = "";

            while (rowIterator.hasNext()) {

                colInt = 1;
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.println("CELL TYPE NUM");
                            System.out.print(cell.getNumericCellValue() + "column : " + colInt + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            if (rowInt != 1) {
                                if (colInt == 1) {
                                    System.out.println("CODE ARTICLE : :  " + cell.getStringCellValue().trim());
                                    // listOfLotsFromExcel.add(cell.getStringCellValue().trim());
                                    articleOfRow = cell.getStringCellValue();
                                } else if (colInt < 5) {
                                    System.out.println("NO USE");
                                } else {
                                    System.out.println("LOT :: " + colInt + " --- VALUE :: " + cell.getStringCellValue());
                                    listOfValuesPerArticle.add(cell.getStringCellValue());
                                }
                            }

                            break;
                    }
                    colInt++;
                }

                System.out.println("END OF ROW : " + rowInt);

                mapOfArtLotFromXls.put(articleOfRow, new LinkedList<>(listOfValuesPerArticle));
                listOfValuesPerArticle.clear();
                rowInt++;
                System.out.println("ROW : " + rowInt);
            }

            file.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION" + e.getMessage());
            e.printStackTrace();
        }

        return mapOfArtLotFromXls;
    }
/*
    public static List<CarteGasoil> parseExcelFileAfriquia(InputStream sourceXls) {

        List<CarteGasoil> listOfRecords = new LinkedList<>();
        CarteGasoil cgaf;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {

           // FileInputStream file = new FileInputStream(new File(sourceXls));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(sourceXls);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int rowInt = 1;
            int colInt = 1;

           

            while (rowIterator.hasNext()) {
                cgaf = new CarteGasoil();
                colInt = 1;
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly

                    if (rowInt != 1) {
                        switch (colInt) {
                            case 1:
                                System.out.println("STATION : :  " + cell.getStringCellValue().trim());
                                cgaf.setStation(cell.getStringCellValue().trim());
                                break;
                            case 2:
                                System.out.println("DATE : :  " + cell.getStringCellValue().trim());
                                cgaf.setDate(formatter.parse(cell.getStringCellValue().trim()));
                                break;
                            case 3:
                                System.out.println("HEURE : :  " + cell.getStringCellValue().trim());
                                cgaf.setHeure(cell.getStringCellValue().trim());
                                break;
                            case 4:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    System.out.println("CARTE NUM : :  " + cell.getNumericCellValue());
                                    cgaf.setNumCarte(String.valueOf(cell.getNumericCellValue()));
                                    break;
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    System.out.println("CARTE NUM : :  " + cell.getStringCellValue());
                                    cgaf.setNumCarte(cell.getStringCellValue());
                                    break;
                                } else {
                                    break;
                                }

                            case 5:
                                System.out.println("LIB CARTE : :  " + cell.getStringCellValue().trim());
                                cgaf.setLibCarte(cell.getStringCellValue());
                                break;
                            case 6:
                                System.out.println("TYPE : :  " + cell.getStringCellValue().trim());
                                cgaf.setTypeCarte(cell.getStringCellValue().trim());
                                break;
                            case 7:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    System.out.println("PRODUIT : :  " + cell.getNumericCellValue());
                                    cgaf.setProduit(String.valueOf(cell.getNumericCellValue()));
                                    break;
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    System.out.println("PRODUIT : :  " + cell.getStringCellValue());
                                    cgaf.setProduit(cell.getStringCellValue());
                                    break;
                                } else {
                                    break;
                                }

                            case 8:
                                System.out.println("LIBELLE : :  " + cell.getStringCellValue().trim());
                                cgaf.setLibelle(cell.getStringCellValue().trim());
                                break;
                            case 9:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    System.out.println("MONTANT : :  " + cell.getNumericCellValue());
                                    cgaf.setMontant(cell.getNumericCellValue());
                                    break;
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    System.out.println("MONTANT STRING : :  " + cell.getStringCellValue());
                                    break;
                                } else {
                                    break;
                                }

                            case 10:

                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    System.out.println("MATRICULE : :  " + cell.getNumericCellValue());
                                    cgaf.setMatricule_(String.valueOf(cell.getNumericCellValue()));
                                    break;
                                    
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    System.out.println("MATRICULE: :  " + cell.getStringCellValue().trim());
                                    cgaf.setMatricule_(cell.getStringCellValue());
                                    break;
                                } else {
                                    break;
                                }

                            case 11:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    System.out.println("KILOMETRAGE : :  " + cell.getNumericCellValue());
                                    cgaf.setKilometrage(cell.getNumericCellValue());
                                    break;
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    System.out.println("KILOMETRAGE STRING : :  " + cell.getStringCellValue().trim());

                                    break;
                                } else {
                                    break;
                                }

                            case 12:

                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    System.out.println("QUANTITE : :  " + cell.getNumericCellValue());
                                    cgaf.setQuantite(cell.getNumericCellValue());
                                    break;
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    System.out.println("QUANTITE STRING : :  " + cell.getStringCellValue().trim());

                                    break;
                                } else {
                                    break;
                                }

                        }
//                                if (colInt == 1) {
//                                    System.out.println("STATION : :  " + cell.getStringCellValue().trim());
//                                    // listOfLotsFromExcel.add(cell.getStringCellValue().trim());                                    
//                                } else if (colInt == 5) {
//                                    System.out.println("NO USE");
//                                } else {
//                                    System.out.println("LOT :: " + colInt + " --- VALUE :: " + cell.getStringCellValue());
//                                    listOfValuesPerArticle.add(cell.getStringCellValue());
//                                }
                    }

                    colInt++;
                }

                System.out.println("END OF ROW : " + rowInt);
                listOfRecords.add(cgaf);
                rowInt++;
                System.out.println("ROW : " + rowInt);
            }

           // file.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION" + e.getMessage());
            e.printStackTrace();
        }

        return listOfRecords;
    }
*/
}
