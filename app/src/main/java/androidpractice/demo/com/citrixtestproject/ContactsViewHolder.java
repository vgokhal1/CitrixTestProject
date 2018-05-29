package androidpractice.demo.com.citrixtestproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactsViewHolder extends RecyclerView.ViewHolder {

    TextView contactName;
    ImageView expand;
    LinearLayout detailsLayout;
    RelativeLayout nameLayout;

    public TextView getContactName() {
        return contactName;
    }

    public void setContactName(TextView contactName) {
        this.contactName = contactName;
    }

    public ImageView getExpand() {
        return expand;
    }

    public void setExpand(ImageView expand) {
        this.expand = expand;
    }

    public LinearLayout getDetailsLayout() {
        return detailsLayout;
    }

    public void setDetailsLayout(LinearLayout detailsLayout) {
        this.detailsLayout = detailsLayout;
    }

    public RelativeLayout getNameLayout() {
        return nameLayout;
    }

    public void setNameLayout(RelativeLayout nameLayout) {
        this.nameLayout = nameLayout;
    }

    public ContactsViewHolder(View itemView) {
        super(itemView);

        contactName = (TextView) itemView.findViewById(R.id.contact_name);
        expand = (ImageView) itemView.findViewById(R.id.expand);
        detailsLayout = (LinearLayout) itemView.findViewById(R.id.contact_details_layout);
        nameLayout = (RelativeLayout) itemView.findViewById(R.id.name_layout);

    }
}
