import com.azure.ai.vision.imageanalysis.ImageAnalysisClient;
import com.azure.ai.vision.imageanalysis.ImageAnalysisClientBuilder;
import com.azure.core.credential.KeyCredential;
import com.azure.ai.vision.imageanalysis.ImageAnalysisClientBuilder;
import com.azure.core.credential.KeyCredential;

import com.azure.ai.vision.imageanalysis.models.ImageAnalysisOptions;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.util.BinaryData;

import java.util.Arrays;
import java.io.File;

public class Principal {

    String endpoint = System.getenv("https://zennoia.cognitiveservices.azure.com/");
    String key = System.getenv("6ff23828021545f5b609105c9eedc180");

    if(endpoint==null||key==null)
    {
        System.out.println("Missing environment variable 'https://zennoia.cognitiveservices.azure.com/' or '6ff23828021545f5b609105c9eedc180'.");
        System.out.println("Set them before running this sample.");
        System.exit(1);
    }

    // Create a synchronous Image Analysis client.
    ImageAnalysisClient client = new ImageAnalysisClientBuilder()
            .endpoint(endpoint)
            .credential(new KeyCredential(key))
            .buildClient();

    // Create an asynchronous Image Analysis client.
    ImageAnalysisAsyncClient client = new ImageAnalysisClientBuilder()
            .endpoint(endpoint)
            .credential(new KeyCredential(key))
            .buildAsyncClient();

    ImageAnalysisResult result = client.analyze(
            BinaryData.fromFile(new File("sample.jpg").toPath()), // imageData: Image file loaded into memory as
                                                                  // BinaryData
            Arrays.asList(VisualFeatures.CAPTION), // visualFeatures
            new ImageAnalysisOptions().setGenderNeutralCaption(true)); // options: Set to 'true' or 'false' (relevant
                                                                       // for CAPTION or DENSE_CAPTIONS visual features)

    // Print analysis results to the console
    System.out.println("Image analysis results:");System.out.println(" Caption:");System.out.println("   \""+result.getCaption().getText()+"\", Confidence "+String.format("%.4f",result.getCaption().getConfidence()));

    ImageAnalysisResult result = client.analyzeFromUrl(
            "https://aka.ms/azsdk/image-analysis/sample.jpg", // imageUrl: the URL of the image to analyze
            Arrays.asList(VisualFeatures.CAPTION), // visualFeatures
            new ImageAnalysisOptions().setGenderNeutralCaption(true)); // options: Set to 'true' or 'false' (relevant
                                                                       // for CAPTION or DENSE_CAPTIONS visual features)

    // Print analysis results to the console
    System.out.println("Image analysis results:");System.out.println(" Caption:");System.out.println("   \""+result.getCaption().getText()+"\", Confidence "+String.format("%.4f",result.getCaption().getConfidence()));

    ImageAnalysisResult result = client.analyze(
            BinaryData.fromFile(new File("sample.jpg").toPath()), // imageData: Image file loaded into memory as
                                                                  // BinaryData
            Arrays.asList(VisualFeatures.READ), // visualFeatures
            null); // options: There are no options for READ visual feature

    // Print analysis results to the console
    System.out.println("Image analysis results:");System.out.println(" Read:");for(
    DetectedTextLine line:result.getRead().getBlocks().get(0).getLines())
    {
        System.out.println("   Line: '" + line.getText()
                + "', Bounding polygon " + line.getBoundingPolygon());
        for (DetectedTextWord word : line.getWords()) {
            System.out.println("     Word: '" + word.getText()
                    + "', Bounding polygon " + word.getBoundingPolygon()
                    + ", Confidence " + String.format("%.4f", word.getConfidence()));
        }
    }
}
