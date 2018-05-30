package androidpractice.demo.com.citrixtestproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private ArrayList<Contacts> contactsList;
    private ArrayList<Contacts> displayContactsList;
    private Context ctx;
    private LayoutInflater layoutInflater;

    private int currentSelectedElement = -1;

    public ContactsRecyclerAdapter(MainActivity mainActivity, ArrayList<Contacts> contactsList) {
        this.ctx = mainActivity;
        this.contactsList = contactsList;
        this.displayContactsList = contactsList;
        this.layoutInflater = LayoutInflater.from(mainActivity);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        View view = layoutInflater.inflate(R.layout.contacts_recyclerview_holder,parent,false);
        viewHolder = new ContactsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ContactsViewHolder contactsViewHolder = (ContactsViewHolder) holder;

        RelativeLayout elementLayout = contactsViewHolder.getNameLayout();

        if (elementLayout!=null){

            contactsViewHolder.getContactName().setText(displayContactsList.get(position).getContactName());

            //Only show details for contact that has been clicked....
            if (currentSelectedElement == position){

                contactsViewHolder.getExpand().setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_unexplore));

                LinearLayout detailsLayout = contactsViewHolder.getDetailsLayout();
                detailsLayout.removeAllViews();
                detailsLayout.setVisibility(View.VISIBLE);

                //Default label "Details"...
                RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView tv=new TextView(ctx);
                tv.setLayoutParams(lparams);
                tv.setText("Details:");
                detailsLayout.addView(tv);

                for (String key:displayContactsList.get(position).getContactDetails().keySet()){

                    RelativeLayout.LayoutParams lparams2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv2=new TextView(ctx);
                    tv2.setLayoutParams(lparams2);
                    tv2.setText("\t"+key+":");
                    detailsLayout.addView(tv2);

                    ArrayList<String> detailsarray = displayContactsList.get(position).getContactDetails().get(key);

                    for(int i=0;i<detailsarray.size();i++){

                        RelativeLayout.LayoutParams lparams3 = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView tv3=new TextView(ctx);
                        tv3.setLayoutParams(lparams3);
                        tv3.setText("\t\t"+detailsarray.get(i));
                        detailsLayout.addView(tv3);

                    }


                }


            }
            else{

                contactsViewHolder.getExpand().setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_explore_button));
                contactsViewHolder.getDetailsLayout().removeAllViews();
                contactsViewHolder.getDetailsLayout().setVisibility(View.GONE);

            }


            elementLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (currentSelectedElement==position) {
                        currentSelectedElement = -1;
                    }
                    else {
                        currentSelectedElement = position;
                    }

                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return displayContactsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence searchString) {
                FilterResults oReturn = null;
                ArrayList<Contacts> results;

                if (!TextUtils.isEmpty(searchString))
                {
                    oReturn = new FilterResults();
                    results = new ArrayList<>();

                    for(Contacts contacts:contactsList){

                        if (contacts.getContactName().toLowerCase().contains(((String) searchString).toLowerCase())){
                            results.add(contacts);
                        }
                    }
                    oReturn.values = results;
                }

                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (!TextUtils.isEmpty(charSequence) && filterResults!=null)
                {
                    displayContactsList = (ArrayList<Contacts>) filterResults.values;
                    notifyDataSetChanged();
                }
                else
                {
                    constructDisplayList();
                }
            }
        };
    }

    private void constructDisplayList() {
        displayContactsList = contactsList;
    }
}
