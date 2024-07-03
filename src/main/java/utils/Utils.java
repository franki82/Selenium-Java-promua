package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Utils extends WaitingsAndVerifications {
    Properties properties;

    public void loadPropertyFile(String pathToProperty) {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(pathToProperty));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        String property = properties.getProperty(key);
        return property;
    }

    public void getScreenshotAndWriteLogAfterException(WebDriver driver, String pathToLocalLog, ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            printMessage(testResult.getThrowable().getMessage());
            generateAndCopyScreenshot(driver, pathToLocalLog);
        }
    }

    String generateAndCopyScreenshot(WebDriver driver, String pathToLocalLog) throws IOException{
        DateFormat df = new SimpleDateFormat("yyyyMMdd-HH-mm-ss-SSS");
        String sdt = df.format(new Date(System.currentTimeMillis()));
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String scrinFileName = "screenshot".concat(sdt).concat(".png");
        printMessage("SCREENSHOT: ".concat(scrinFileName));
        try {
            FileUtils.copyFile(scrFile, new File(pathToLocalLog.concat(scrinFileName)));
        } catch (Exception e) {
            printMessage("Something wrong with copy screenshot file" );
        }

        return scrinFileName;
    }

    public Object getRunJsQuery(WebDriver driver, String jsQuery){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return  js.executeScript(jsQuery);
    }

    public Object getRunJsQuery(WebDriver driver, String jsQuery, WebElement element){
        return ((JavascriptExecutor)driver).executeScript(jsQuery, element);
    }

    public String getVideoDurationQuery(String videoPlayerId){
        return "return document.getElementById('" + videoPlayerId +"').duration";
    }

    public String getVideoCurrentTimeQuery(String videoPlayerId){
        return "return document.getElementById('" + videoPlayerId +"').currentTime";
    }

    public int parseStringTimeInteger(String unparsedTimeString){
        String[] timeUnits = unparsedTimeString.split(":");
        int hours = Integer.parseInt(timeUnits[0]);
        int minutes = Integer.parseInt(timeUnits[1]);
        int seconds = Integer.parseInt(timeUnits[2]);
        return 3600 * hours + 60 * minutes + seconds;
    }

    public String getImagePreviewWidth(String imagePreviewId){
        return "return document.getElementById('" + imagePreviewId +"').clientWidth";
    }

    public String getImagePreviewHeight(String imagePreviewId){
        return "return document.getElementById('" + imagePreviewId +"').clientHeight";
    }

    public String getImagePreviewOffsetLeft(String imagePreviewId){
        return "return document.getElementById('" + imagePreviewId +"').offsetLeft";
    }

    public String getImagePreviewOffsetTop(String imagePreviewId){
        return "return document.getElementById('" + imagePreviewId +"').offsetTop";
    }

    public String getSearchUrlCursorPosition(){
        return "return document.getSelection().getRangeAt(0).startOffset";
    }

    public String getDateTimeLocalFromUTC(String inputLocalISO){
        //input format like "2020-12-05T13:18:02"
        DateTimeFormatter formatterParsed = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Calendar now = Calendar.getInstance();
        TimeZone timeZone = now.getTimeZone();
        Instant timeStampUTC = Instant.parse(inputLocalISO+"Z");
        ZonedDateTime localTime = timeStampUTC.atZone(ZoneId.of(timeZone.getID()));
        if (now.get(Calendar.DST_OFFSET) > 0) {
            localTime = localTime.plusHours(1);
        }
        return localTime.format(formatterParsed);
    }

    public String getDateTimeUTCFromLocal(String inputUTCISO){
        //input format like "2020-12-05T13:18:02"
        DateTimeFormatter formatterParsed = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss");
        ZoneId timeZoneLocal = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = LocalDateTime.parse(inputUTCISO, DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(timeZoneLocal);
        ZonedDateTime zonedUTCDateTime = zonedDateTime.minusSeconds(zonedDateTime.getOffset().getTotalSeconds());
        return zonedUTCDateTime.format(formatterParsed);
    }

    public String convertDateTimeToISO(String inputDateTime){
        //input format like Feb 25, 2020 14:36:01
        String monthName = inputDateTime.substring(0, 3);
        String day = inputDateTime.substring(4, 6);
        String year = inputDateTime.substring(8, 12);
        String time = inputDateTime.substring(13);
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(monthName);
        Integer monthInt = accessor.get(ChronoField.MONTH_OF_YEAR);
        if (monthInt < 10){
            monthName = "0" + monthInt.toString();
        } else {
            monthName = monthInt.toString();
        }
        return year + "-" + monthName + "-" + day + "T"+time;
    }

    public List<String> dataStringFromClipboard() throws Exception {
        String dataClipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        return new ArrayList<String>(Arrays.asList(dataClipboard.split("\\r?\\n")));
    }

    public void verifyOrder(List<String> actualNames, String sortingOrder) throws Exception {
        List<String> sortedNames = (List<String>) ((ArrayList<String>) actualNames).clone();
        if (sortingOrder.equals("asc")) {
            Collections.sort(sortedNames);
        } else {
            Collections.sort(sortedNames, Collections.reverseOrder());
        }
        printMessage("Sorted: " + sortedNames);
        verifyTrue(actualNames.equals(sortedNames), "Values list have wrong order");
    }

    public void getCurrentUTCTimezone(){
        ZoneOffset offsetZone = OffsetDateTime.now().getOffset();
        printMessage("UTC Timezone offset: " + offsetZone.toString());
    }

    public void clickToESC(WebDriver driver){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void clickToSymbol(WebDriver driver, String symbolToClick){
        Actions actions = new Actions(driver);
        actions.sendKeys(symbolToClick).build().perform();
    }

    public void clickToCTRLAndSymbol(WebDriver driver, String symbolToClick){
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys(symbolToClick).keyUp(Keys.CONTROL).build().perform();
    }

    public void clickToSHIFTAndSymbol(WebDriver driver, String symbolToClick){
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.SHIFT).sendKeys(symbolToClick).keyUp(Keys.SHIFT).build().perform();
    }

    public List<String> convertationStringToList(String primaryStringData, String separatedSign){
        List<String> stringItemsList = new ArrayList<>();
        for(String currentItem : Arrays.asList(primaryStringData.split(separatedSign))){
            stringItemsList.add(currentItem.trim());
        }
        return stringItemsList;
    }

    public void compareStringLists(List<String> firstList, List<String> secondList) throws Exception {
        List <String> lowercaseFirstList = firstList.stream().map(String::toLowerCase).collect(Collectors.toList());
        List <String> lowercaseSecondList = secondList.stream().map(String::toLowerCase).collect(Collectors.toList());
        printMessage("First List (lowercase): " + lowercaseFirstList);
        printMessage("Second List (lowercase): " + lowercaseSecondList);
        verifyTrue(lowercaseFirstList.equals(lowercaseSecondList), "Lists are not equal (lowercase)");
    }

    public Date convertationStringToDate(String stringData) throws Exception {
        String patternDate = "MMM dd, yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternDate, Locale.US);
        return simpleDateFormat.parse(stringData);
    }
}
