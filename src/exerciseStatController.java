
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class exerciseStatController {

    @FXML
    private Label pushUpLabel;

    @FXML
    private Label fBenchLabel;

    @FXML
    private Label dBenchLabel;

    @FXML
    private Label iBenchLabel;

    @FXML
    private Label squatLabel;

    private Statistics statistics;

    public void processDialog(DailyData data){
        statistics = new Statistics();
        pushUpLabel.setText(statistics.getMaxPushUps(data));
        fBenchLabel.setText(statistics.getMaxFlatBench(data));
        dBenchLabel.setText(statistics.getMaxDeclineBench(data));
        iBenchLabel.setText(statistics.getMaxInclineBench(data));
        squatLabel.setText(statistics.getMaxSquat(data));
    }
}
