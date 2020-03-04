package SpaceSolver.co;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.CodaBarWriter;

public class MainActivity extends AppCompatActivity
{
    // バーコードの各種設定
    String targetData = "";       //バーコードに変換する対象データ
    int    width      = 400;      //作成するバーコードの幅
    int    height     = 200;      //作成するバーコードの高さ

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void DispCodaBar(String targetData, int width, int height)
    {
        // CODABAR規格用のデータ変換クラスをインスタンス化する
        CodaBarWriter writer = new CodaBarWriter();

        try {
            // 対象データを変換する
            BitMatrix bitMatrix = writer.encode(targetData, BarcodeFormat.CODABAR, width, height);      //...(1)

            // BitMatrixのデータが「true」の時は「黒」を設定し、「false」の時は「白」を設定する              //...(2)
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++)
            {
                int offset = y * width;
                for (int x = 0; x < width; x++)
                {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            DispBitmapImage(pixels, width, height);
        }
        catch (WriterException e)
        {
        }
    }

    public void DispBitmapImage(int[] pixels, int width, int height)
    {
        // ビットマップ形式に変換する
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        ImageView imageView = findViewById(R.id.imageView);
        // イメージビューに表示する
        imageView.setImageBitmap(bitmap);
    }

    public void OnClickAddButton(View view)
    {
        EditText editText = findViewById(R.id.inputNumber);
        // 入力データをバーコードに変換する対象データに代入
        targetData = editText.getText().toString();

        // バーコードフォーマット種別を選択

        // バーコード表示処理
        DispCodaBar(targetData, width, height);

        // 取得したテキストを TextView に張り付ける
        //textView.setText(text);
    }

    public void OnClickClearButton(View view)
    {
        EditText editText = findViewById(R.id.inputNumber);
        // 入力データを消去
        editText.setText("");

        // バーコード表示を非表示にする。

    }
}
