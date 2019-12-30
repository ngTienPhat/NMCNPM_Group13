package group13.ntphat.evernote.ui.notes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import group13.ntphat.evernote.R;
import xute.markdeditor.EditorControlBar;
import xute.markdeditor.MarkDEditor;
import xute.markdeditor.datatype.DraftDataItemModel;
import xute.markdeditor.models.DraftModel;

import static xute.markdeditor.Styles.TextComponentStyle.BLOCKQUOTE;
import static xute.markdeditor.Styles.TextComponentStyle.H1;
import static xute.markdeditor.Styles.TextComponentStyle.H3;
import static xute.markdeditor.Styles.TextComponentStyle.NORMAL;
import static xute.markdeditor.components.TextComponentItem.MODE_OL;
import static xute.markdeditor.components.TextComponentItem.MODE_PLAIN;

public class ViewNoteActivity extends AppCompatActivity implements EditorControlBar.EditorControlListener  {
    private final int REQUEST_IMAGE_SELECTOR = 110;
    private MarkDEditor markDEditor;
    private EditorControlBar editorControlBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_editor);
    }

    @Override
    public void onInsertImageClicked() {
        //openGallery();
    }

    @Override
    public void onInserLinkClicked() {
        //markDEditor.addLink("Click Here", "http://www.hapramp.com");

    }


    // Init note content
    private DraftModel initDraftContent() {
        ArrayList<DraftDataItemModel> contentTypes = new ArrayList<>();
        DraftDataItemModel heading = new DraftDataItemModel();
        heading.setItemType(DraftModel.ITEM_TYPE_TEXT);
        heading.setContent("NM. CNPM");
        heading.setMode(MODE_PLAIN);
        heading.setStyle(H1);

        DraftDataItemModel sub_heading = new DraftDataItemModel();
        sub_heading.setItemType(DraftModel.ITEM_TYPE_TEXT);
        sub_heading.setContent("Nominated");
        sub_heading.setMode(MODE_PLAIN);
        sub_heading.setStyle(H3);

        DraftDataItemModel bl = new DraftDataItemModel();
        bl.setItemType(DraftModel.ITEM_TYPE_TEXT);
        bl.setContent("A super star of south movies!");
        bl.setMode(MODE_PLAIN);
        bl.setStyle(BLOCKQUOTE);

        DraftDataItemModel body = new DraftDataItemModel();
        body.setItemType(DraftModel.ITEM_TYPE_TEXT);
        body.setContent("\n" +
                "Kajal Aggarwal in March 2017\n" +
                "Kajal Aggarwal is an Indian actress who appears in primarily in Tamil and Telugu films.[1] She made her acting debut with a minor role in the Hindi film Kyun! Ho Gaya Na..., a box office failure. She later signed up P. Bharathiraja's Tamil film Bommalattam, which was to have been her first film in that language, but it was delayed.");
        body.setMode(MODE_PLAIN);
        body.setStyle(NORMAL);

        DraftDataItemModel hrType = new DraftDataItemModel();
        hrType.setItemType(DraftModel.ITEM_TYPE_HR);

        DraftDataItemModel imageType = new DraftDataItemModel();
        imageType.setItemType(DraftModel.ITEM_TYPE_IMAGE);
        imageType.setDownloadUrl("https://cdn.shopify.com/s/files/1/0166/3704/products/78008-3_grande.jpg");
        imageType.setCaption("Cute Pink Photo");

        DraftDataItemModel filmsList1 = new DraftDataItemModel();
        filmsList1.setItemType(DraftModel.ITEM_TYPE_TEXT);
        filmsList1.setStyle(NORMAL);
        filmsList1.setMode(MODE_OL);
        filmsList1.setContent("2009 – Filmfare Award for Best Actress – Telugu for Magadheera");

        DraftDataItemModel filmsList2 = new DraftDataItemModel();
        filmsList2.setItemType(DraftModel.ITEM_TYPE_TEXT);
        filmsList2.setStyle(NORMAL);
        filmsList2.setMode(MODE_OL);
        filmsList2.setContent("2010 – Filmfare Award for Best Actress – Telugu for Darling");
        contentTypes.add(heading);
        contentTypes.add(filmsList1);
        contentTypes.add(imageType);
        DraftModel contentModel = new DraftModel(contentTypes);
        return contentModel;
    }


}
