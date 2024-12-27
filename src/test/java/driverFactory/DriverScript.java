package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	String inputpath="./FileInput/DataEngine.xlsx";
	String outputpath="./FileOutput/HybridResults.xlsx";
	String TestCases="MasterTestCases";
	WebDriver driver;
	
public void startTest() throws Throwable {
	String Module_status="";
	String Module_new="";
	//Create object to call excel methods
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//Iterate all rows in MasterTestCases sheet
	for(int i=1;i<=xl.rowCount(TestCases);i++) {
		if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y")) {
			//Store corresponding sheet into one variable
			String TCModule=xl.getCellData(TestCases, i, 1);
			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++) {
				String Description=xl.getCellData(TCModule, j, 0);
				String Object_Type=xl.getCellData(TCModule, j, 1);
				String Ltype=xl.getCellData(TCModule, j, 2);
				String Lvalue=xl.getCellData(TCModule, j, 3);
				String Test_Data=xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("stratBrowser")) {
						driver=FunctionLibrary.stratBrowser();
					}
					if(Object_Type.equalsIgnoreCase("openUrl"));{
					FunctionLibrary.openUrl();
					}
					if(Object_Type.equalsIgnoreCase("waitForElement")) {
						FunctionLibrary.waitForElement(Ltype, Lvalue, Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("typeAction")) {
						FunctionLibrary.typeAction(Ltype, Lvalue, Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("clickAction")) {
						FunctionLibrary.clickAction(Ltype, Lvalue);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle")) {
						FunctionLibrary.validateTitle(Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser")) {
						FunctionLibrary.closeBrowser();
					}
					//Write as pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					Module_status="True";
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					//Write as Fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					Module_new="False";
				}
				if(Module_status.equalsIgnoreCase("True")) {
					xl.setCellData(TestCases, i, 3, "Pass", outputpath);
				}
				else {
					//Write as fail into Test Cases Sheet
					xl.setCellData(TestCases, i, 3, "Fail", outputpath);
				}
			}
		}
		else {
			//Write as blocked in status cell for text cases flag to N
			xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
		}
	}
}

}