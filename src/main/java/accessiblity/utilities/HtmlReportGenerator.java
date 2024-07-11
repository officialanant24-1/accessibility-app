package accessiblity.utilities;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlReportGenerator {
    private StringBuilder htmlReport;

    public HtmlReportGenerator() {
        this.htmlReport = new StringBuilder();
    }

    public void startHtmlReport() {
        htmlReport.append("<!DOCTYPE html>");
        htmlReport.append("<html lang='en'>");
        htmlReport.append("<head>");
        htmlReport.append("<meta charset='UTF-8'>");
        htmlReport.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        htmlReport.append("<title>Combined Accessibility Report</title>");
        htmlReport.append("<style>");
        htmlReport.append("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; }");
        htmlReport.append("h1, h2 { color: #333; }");
        htmlReport.append(".container { width: 90%; margin: 0 auto; padding: 20px; }");
        htmlReport.append(".violation { margin-bottom: 20px; padding: 10px; border: 1px solid #ccc; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        htmlReport.append(".violation h2 { margin-top: 0; cursor: pointer; }");
        htmlReport.append(".violation .impact { font-weight: bold; color: #d9534f; }");
        htmlReport.append(".violation .helpUrl { font-style: italic; }");
        htmlReport.append(".node { margin-left: 20px; display: none; }");
        htmlReport.append("button { padding: 10px 20px; margin: 20px 0; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }");
        htmlReport.append("button:hover { background-color: #0056b3; }");
        htmlReport.append("</style>");
        htmlReport.append("</head>");
        htmlReport.append("<body>");
        htmlReport.append("<div class='container'>");
        htmlReport.append("<h1>Combined Accessibility Report</h1>");
        htmlReport.append("<button onclick='toggleAll()'>Toggle All</button>");
    }

    public void addResultsToHtmlReport(Results results, String tag) {
        htmlReport.append("<h2>Results for ").append(tag).append("</h2>");

        if (results.getViolations().isEmpty()) {
            htmlReport.append("<p>No violations found for ").append(tag).append("</p>");
        } else {
            for (Rule violation : results.getViolations()) {
                htmlReport.append("<div class='violation'>");
                htmlReport.append("<h2 onclick='toggleVisibility(this)'>").append(violation.getDescription()).append("</h2>");
                htmlReport.append("<div>");
                htmlReport.append("<p class='impact'>Impact: ").append(violation.getImpact()).append("</p>");
                htmlReport.append("<p class='helpUrl'>Help: <a href='").append(violation.getHelpUrl()).append("' target='_blank'>").append(violation.getHelpUrl()).append("</a></p>");
                for (com.deque.html.axecore.results.Node node : violation.getNodes()) {
                    htmlReport.append("<div class='node'>");
                    htmlReport.append("<p>HTML: ").append(node.getHtml()).append("</p>");
                    htmlReport.append("</div>");
                }
                htmlReport.append("</div>");
                htmlReport.append("</div>");
            }
        }
    }

    public void endHtmlReport() {
        htmlReport.append("</div>");
        htmlReport.append("<script>");
        htmlReport.append("function toggleVisibility(element) {");
        htmlReport.append("var content = element.nextElementSibling;");
        htmlReport.append("if (content.style.display === 'none' || content.style.display === '') {");
        htmlReport.append("content.style.display = 'block';");
        htmlReport.append("} else {");
        htmlReport.append("content.style.display = 'none';");
        htmlReport.append("}");
        htmlReport.append("}");
        htmlReport.append("function toggleAll() {");
        htmlReport.append("var nodes = document.querySelectorAll('.node');");
        htmlReport.append("nodes.forEach(node => {");
        htmlReport.append("if (node.style.display === 'none' || node.style.display === '') {");
        htmlReport.append("node.style.display = 'block';");
        htmlReport.append("} else {");
        htmlReport.append("node.style.display = 'none';");
        htmlReport.append("}");
        htmlReport.append("});");
        htmlReport.append("}");
        htmlReport.append("</script>");
        htmlReport.append("</body>");
        htmlReport.append("</html>");
    }

    public void saveHtmlReport(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlReport.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
