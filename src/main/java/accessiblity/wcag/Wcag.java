package accessiblity.wcag;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.openqa.selenium.WebDriver;
import accessiblity.utilities.HtmlReportGenerator;
import java.util.Arrays;
import java.util.List;

public class Wcag {
    private WebDriver driver;
    private Results axeResults;
    private HtmlReportGenerator htmlReportGenerator;

    public Wcag(WebDriver driver) {
        this.driver = driver;
        this.htmlReportGenerator = new HtmlReportGenerator();
    }

    public void testAccessibility(String url) {
        List<String> tags = Arrays.asList("wcag2a", "wcag2aa", "wcag2aaa", "wcag21a", "wcag21aa", "wcag22aa");

        htmlReportGenerator.startHtmlReport();

        for (String tag : tags) {
            AxeBuilder builder = new AxeBuilder().withTags(Arrays.asList(tag));
            driver.get(url);

            try {
                axeResults = builder.analyze(driver);
                if (axeResults.getViolations().isEmpty()) {
                    System.out.println("No violations found for tag: " + tag);
                } else {
                    axeResults.getViolations().forEach(violation -> {
                        System.out.println(violation.getDescription());
                        violation.getNodes().forEach(node -> {
                            System.out.println(node.getHtml());
                        });
                    });
                }
                // Add the results for the current tag to the combined report
                htmlReportGenerator.addResultsToHtmlReport(axeResults, tag);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        htmlReportGenerator.endHtmlReport();
        driver.quit();
        htmlReportGenerator.saveHtmlReport("combined-accessibility-report.html");
    }

    public Results getResults() {
        return axeResults;
    }
}