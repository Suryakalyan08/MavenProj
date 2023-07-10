package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath ="./FileInput/Controller.xlsx";
String outputpath ="./FileOutPut/HybridResults.xlsx";
String Testcases="MasterTestCases";
public void startTest()throws Throwable
{
	String Modulestatus="";
	//create reference object for Excelfile util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in testcases sheet
	for(int i=1;i<=xl.rowCount(Testcases);i++)
	{
		if(xl.getCellData(Testcases, i, 2).equalsIgnoreCase("Y"))
		{
			//read corresponding sheet 
			String TCModule= xl.getCellData(Testcases, i, 1);
			//iterate each Sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read cells from TCModule
				String Descrition =xl.getCellData(TCModule, j, 0);
				String Function_Name =xl.getCellData(TCModule, j, 1);
				String Locator_Type =xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Function_Name.equalsIgnoreCase("startBrowser"))
					{
						driver=FunctionLibrary.startBrowser();
					}
					else if(Function_Name.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl(driver);
					}
					else if(Function_Name.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Function_Name.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Function_Name.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
					}
					else if(Function_Name.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(driver, Test_Data);
					}
					else if(Function_Name.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
					}
					else if(Function_Name.equalsIgnoreCase("selectDropDown"))
					{
						FunctionLibrary.selectDropDown(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Function_Name.equalsIgnoreCase("capturestockitem"))
					{
						FunctionLibrary.capturestockitem(driver, Locator_Type, Locator_Value);
					}
					else if(Function_Name.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable(driver);
					}
					else if(Function_Name.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibrary.mouseClick(driver);
					}
					else if(Function_Name.equalsIgnoreCase("categoryTable"))
					{
						FunctionLibrary.categoryTable(driver, Test_Data);
					}
					//write as pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					Modulestatus="True";
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//write as fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					Modulestatus="False";
				}
				if(Modulestatus.equalsIgnoreCase("True"))
				{
					//write as pass into Testcase sheet
					xl.setCellData(Testcases, i, 3, "Pass", outputpath);
				}
				if(Modulestatus.equalsIgnoreCase("False"))
				{
					xl.setCellData(Testcases, i, 3, "Fail", outputpath);
				}

			}
		}
		else
		{
			//write as blocked into Testcases sheet which are flag to N
			xl.setCellData(Testcases, i, 3, "Blocked", outputpath);
		}
	}
}
}
