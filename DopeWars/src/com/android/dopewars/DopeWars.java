package com.android.dopewars;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.android.games.dopewars.R;

public class DopeWars extends Activity implements View.OnTouchListener {
	private TextView tvAcidDrugs, tvCocaineDrugs, tvEcstasyDrugs, tvPCPDrugs,
					 tvHeroinDrugs, tvWeedDrugs, tvShroomsDrugs, tvSpeedDrugs;
	private TextView tvAcidPrice, tvCocainePrice, tvEcstasyPrice, tvPCPPrice,
					 tvHeroinPrice, tvWeedPrice, tvShroomsPrice, tvSpeedPrice;
	private TextView tvZone, tvCash, tvDebt, tvSavings, tvCoat, tvDays;
	private Button btLoanShark, btBank;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		user = new User();
		
		Jet(this.findViewById(R.id.btJet));
		
		//String zone = getIntent().getExtras().getString("zone");
		//int zoneint = getIntent().getExtras().getInt("zoneint");
		
		tvAcidDrugs = (TextView)findViewById(R.id.tvAcidDrugs);
		tvCocaineDrugs = (TextView)findViewById(R.id.tvCocaineDrugs);
		tvEcstasyDrugs = (TextView)findViewById(R.id.tvEcstasyDrugs);
		tvPCPDrugs = (TextView)findViewById(R.id.tvPCPDrugs);
		tvHeroinDrugs = (TextView)findViewById(R.id.tvHeroinDrugs);
		tvWeedDrugs = (TextView)findViewById(R.id.tvWeedDrugs);
		tvShroomsDrugs = (TextView)findViewById(R.id.tvShroomsDrugs);
		tvSpeedDrugs = (TextView)findViewById(R.id.tvSpeedDrugs);
		
		tvAcidPrice = (TextView)findViewById(R.id.tvAcidPrice);
		tvCocainePrice = (TextView)findViewById(R.id.tvCocainePrice);
		tvEcstasyPrice = (TextView)findViewById(R.id.tvEcstasyPrice);
		tvPCPPrice = (TextView)findViewById(R.id.tvPCPPrice);
		tvHeroinPrice = (TextView)findViewById(R.id.tvHeroinPrice);
		tvWeedPrice = (TextView)findViewById(R.id.tvWeedPrice);
		tvShroomsPrice = (TextView)findViewById(R.id.tvShroomsPrice);
		tvSpeedPrice = (TextView)findViewById(R.id.tvSpeedPrice);
		
		tvZone = (TextView)findViewById(R.id.tvZone);
		tvCash = (TextView)findViewById(R.id.tvCash);
		tvDebt = (TextView)findViewById(R.id.tvDebt);
		tvSavings = (TextView)findViewById(R.id.tvSavings);
		tvCoat = (TextView)findViewById(R.id.tvCoat);
		tvDays = (TextView)findViewById(R.id.tvDays);
		
		btLoanShark = (Button)findViewById(R.id.btLoanShark);
		btBank = (Button)findViewById(R.id.btBank);
		
		tvAcidDrugs.setOnTouchListener(this);
		tvCocaineDrugs.setOnTouchListener(this);
		tvEcstasyDrugs.setOnTouchListener(this);
		tvPCPDrugs.setOnTouchListener(this);
		tvHeroinDrugs.setOnTouchListener(this);
		tvWeedDrugs.setOnTouchListener(this);
		tvShroomsDrugs.setOnTouchListener(this);
		tvSpeedDrugs.setOnTouchListener(this);
		
		tvAcidPrice.setOnTouchListener(this);
		tvCocainePrice.setOnTouchListener(this);
		tvEcstasyPrice.setOnTouchListener(this);
		tvPCPPrice.setOnTouchListener(this);
		tvHeroinPrice.setOnTouchListener(this);
		tvWeedPrice.setOnTouchListener(this);
		tvShroomsPrice.setOnTouchListener(this);
		tvSpeedPrice.setOnTouchListener(this);
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String zone = data.getExtras().getString("zone");
			int zoneint = data.getExtras().getInt("zoneint");
			if (zoneint == user.getLocation()) {
				//do nothing cause still in same zone
				//else recalculate everything
			} else {
				tvZone.setText(zone); // Set title to current Zone.
				user.setLocation(zoneint);
				if (zone.compareTo("Bronx") == 0) { // Disable LoanShark and
													// Bank Buttons if not in
													// Bronx
					btLoanShark.setEnabled(true);
					btBank.setEnabled(true);

				} else {
					btLoanShark.setEnabled(false);
					btBank.setEnabled(false);
				}
				user.setTimeLeft(user.getDays() - 1); // Decrease days by 1
				calculatePrices();
				recalculateSavings();
				recalculateDebt();
				setText(); // fill TextViews with prices and drug amounts.
			}
		}

	}

	private void recalculateDebt() {
		long debt = user.getDebt();
		if( user.getDays() < 30 )
			user.setDebt(debt + (debt>>3));			// increase the debt by 1/8 
	}

	private void recalculateSavings() {
		long savings = user.getSavings();
		if( user.getDays() < 30 )
			user.setSavings(savings + (savings>>4)); // increase the savings by 1/10
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// check which textview it is and do what you need to do
		switch(v.getId()){
		case R.id.tvAcidDrugs:
			if(user.getDrugs()[0] > 0)			//check if user has this type of drugs on him.
				showSellDialog(0);				//if so open sell dialog
			else
				Toast.makeText(this, "You have no Acid to sell.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvCocaineDrugs:
			if(user.getDrugs()[1] > 0)
				showSellDialog(1);
			else
				Toast.makeText(this, "You have no Cocaine to sell.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvEcstasyDrugs:
			if(user.getDrugs()[2] > 0)
				showSellDialog(2);
			else
				Toast.makeText(this, "You have no Ecstasy to sell.", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tvPCPDrugs:
			if(user.getDrugs()[3] > 0)
				showSellDialog(3);
			else
				Toast.makeText(this, "You have no PCP to sell.", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tvHeroinDrugs:
			if(user.getDrugs()[4] > 0)
				showSellDialog(4);
			else
				Toast.makeText(this, "You have no Heroin to sell.", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tvWeedDrugs:
			if(user.getDrugs()[5] > 0)
				showSellDialog(5);
			else
				Toast.makeText(this, "You have no Weed to sell.", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tvShroomsDrugs:
			if(user.getDrugs()[6] > 0)
				showSellDialog(6);
			else
				Toast.makeText(this, "You have no Shrooms to sell.", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tvSpeedDrugs:
			if(user.getDrugs()[7] > 0)
				showSellDialog(7);
			else
				Toast.makeText(this, "You have no Speed to sell.", Toast.LENGTH_SHORT).show();
			break;
			
			
			
		case R.id.tvAcidPrice:
			if(user.getDrugPrices()[0] > 0)
				showBuyDialog(0);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
		
		case R.id.tvCocainePrice:
			if(user.getDrugPrices()[1] > 0)
				showBuyDialog(1);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvEcstasyPrice:
			if(user.getDrugPrices()[2] > 0)
				showBuyDialog(2);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvPCPPrice:
			if(user.getDrugPrices()[3] > 0)
				showBuyDialog(3);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvHeroinPrice:
			if(user.getDrugPrices()[4] > 0)
				showBuyDialog(4);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvWeedPrice:
			if(user.getDrugPrices()[5] > 0)
				showBuyDialog(5);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvShroomsPrice:
			if(user.getDrugPrices()[6] > 0)
				showBuyDialog(6);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tvSpeedPrice:
			if(user.getDrugPrices()[7] > 0)
				showBuyDialog(7);
			else
				Toast.makeText(this, "No one is selling this drug.", Toast.LENGTH_SHORT).show();
			break;
		}
		return false;
	}

	private void showSellDialog(final int drug) {
		//set up dialog
        final Dialog dialog = new Dialog(DopeWars.this);
        String message;
        
        dialog.setContentView(R.layout.buyselldialog);
        dialog.setTitle("Sell " + getDrugName(drug));
        dialog.setCancelable(true);
        
        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        message = "You can sell up to " + user.getDrugs()[drug] + " at $" + user.getDrugPrices()[drug] + " each.";
        text.setText(message);
        
        //set numberpicker
        final NumberPicker numbpick = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
        numbpick.setCurrent(user.getDrugs()[drug]);
        numbpick.setmEnd(user.getDrugs()[drug]);

        //set up cancel button
        Button btCancelDialog = (Button) dialog.findViewById(R.id.btCancel);
        btCancelDialog.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //set Cancel up button
        Button button = (Button) dialog.findViewById(R.id.btCancel);
        button.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
                dialog.dismiss();
                
            }
        });
        
        //set up sell button
        Button btSell = (Button) dialog.findViewById(R.id.btTransaction);
        btSell.setText("   Sell   ");
        btSell.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				user.setDrugs(drug,user.getDrugs()[drug] - numbpick.getCurrent());
				user.setDrugsSum(user.getDrugsSum() - numbpick.getCurrent());
				user.setCash(user.getCash() + (numbpick.getCurrent() * user.getDrugPrices()[drug]));
				dialog.dismiss();
				setText();
			}
		});
        
        //now that the dialog is set up, it's time to show it    
        dialog.show();
		
	}
	
	private void showBuyDialog(final int drug) {
		//set up dialog
        final Dialog dialog = new Dialog(DopeWars.this);
        int count;
        String message;
        
        dialog.setContentView(R.layout.buyselldialog);
        dialog.setTitle("Buy " + getDrugName(drug));
        dialog.setCancelable(true);
        
        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        message = "At $" + user.getDrugPrices()[drug] + " each, you can afford ";
        count = (int)(user.getCash() / user.getDrugPrices()[drug]);
        if( count < 1000 )
        	message += count + ".";
        else
        	message += "a lot.";
        text.setText(message);
        
        //set numberpicker
        final NumberPicker numbpick = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
        numbpick.setCurrent(count);
        numbpick.setmEnd(count);

        //set up cancel button
        Button btCancelDialog = (Button) dialog.findViewById(R.id.btCancel);
        btCancelDialog.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        //set up buy button
        Button btBuy = (Button) dialog.findViewById(R.id.btTransaction);
        btBuy.setText("   Buy   ");
        btBuy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				user.setDrugs(drug,user.getDrugs()[drug] + numbpick.getCurrent());
				user.setDrugsSum(user.getDrugsSum() + numbpick.getCurrent());
				user.setCash(user.getCash() - (numbpick.getCurrent() * user.getDrugPrices()[drug]));
				dialog.dismiss();
				setText();
			}
		});
		
        dialog.show();  //now that the dialog is set up, it's time to show it
	}

	public void Jet(View view) {
		Intent myIntent = new Intent(view.getContext(), Jet.class);
		startActivityForResult(myIntent, 0);
	}
	
	public void setText(){
		tvAcidDrugs.setText("" + user.getDrugs()[0]);
		tvCocaineDrugs.setText("" + user.getDrugs()[1]);
		tvEcstasyDrugs.setText("" + user.getDrugs()[2]);
		tvPCPDrugs.setText("" + user.getDrugs()[3]);
		tvHeroinDrugs.setText("" + user.getDrugs()[4]);
		tvWeedDrugs.setText("" + user.getDrugs()[5]);
		tvShroomsDrugs.setText("" + user.getDrugs()[6]);
		tvSpeedDrugs.setText("" + user.getDrugs()[7]);
		
		
		if(user.getDrugPrices()[0] > 0)
			tvAcidPrice.setText("$" + user.getDrugPrices()[0]);
		else
			tvAcidPrice.setText("-");
		
		if(user.getDrugPrices()[1] > 0)
			tvCocainePrice.setText("$" + user.getDrugPrices()[1]);
		else 
			tvCocainePrice.setText("-");
		
		if(user.getDrugPrices()[2] > 0)
			tvEcstasyPrice.setText("$" + user.getDrugPrices()[2]);
		else
			tvEcstasyPrice.setText("-");
		
		if(user.getDrugPrices()[3] > 0)
			tvPCPPrice.setText("$" + user.getDrugPrices()[3]);
		else
			tvPCPPrice.setText("-");
		
		if(user.getDrugPrices()[4] > 0)
			tvHeroinPrice.setText("$" + user.getDrugPrices()[4]);
		else
			tvHeroinPrice.setText("-");
		
		if(user.getDrugPrices()[5] > 0)
			tvWeedPrice.setText("$" + user.getDrugPrices()[5]);
		else
			tvWeedPrice.setText("-");
		
		if(user.getDrugPrices()[6] > 0 )
			tvShroomsPrice.setText("$" + user.getDrugPrices()[6]);
		else
			tvShroomsPrice.setText("-");
		
		if(user.getDrugPrices()[7] > 0)
			tvSpeedPrice.setText("$" + user.getDrugPrices()[7]);
		else
			tvSpeedPrice.setText("-");
		
		tvCash.setText("$" + user.getCash());
		tvDebt.setText("$" +  user.getDebt());
		tvSavings.setText("$" + user.getSavings());
		tvCoat.setText(user.getDrugsSum() + "/" + user.getCoat());
		tvDays.setText("" + user.getDays());
	}
	
	private void calculatePrices() {
		int i, j;
		long drugPrices[] = new long[8];
		Random rand = new Random();
		
		drugPrices[0] = 1000 + Math.abs(rand.nextInt()) % 3500;
		drugPrices[1] = 15000 + Math.abs(rand.nextInt()) % 15000;
		drugPrices[2] = 10 + Math.abs(rand.nextInt()) % 50;
		drugPrices[3] = 1000 + Math.abs(rand.nextInt()) % 2500;
		drugPrices[4] = 5000 + Math.abs(rand.nextInt()) % 9000;
		drugPrices[5] = 300 + Math.abs(rand.nextInt()) % 600;
		drugPrices[6] = 600 + Math.abs(rand.nextInt()) % 750;
		drugPrices[7] = 70 + Math.abs(rand.nextInt()) % 180;
		
		for (i = 0; i < 3; i++){ 		//randomly choose up to three drugs to be zero
			j = rand.nextInt(8);
			drugPrices [j] = 0;
		}
		user.setDrugPrices(drugPrices);
	}
	
	private String getDrugName(int drug){
		switch(drug){
		case 0:
			return "Acid";
		case 1:
			return "Cocaine";
		case 2:
			return "Ecstasy";
		case 3:
			return "PCP";
		case 4:
			return "Heroin";
		case 5:
			return "Weed";
		case 6:
			return "Shrooms";
		case 7:
			return "Speed";
		default:
		return null;
		}
	}
	
	
}