package com.simtoonsoftware.taxthegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Runnable, RewardedVideoAdListener {
    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    // Booleans
    boolean powerBillEnabled = true;
    boolean taxEnabled = true;

    // Long and Integers
    public boolean running = true;
    double version = 0.35;
    public long money;
    public long moneyLabel;

    int printers;
    private int printert1; private int printert1Price = 100; private long printert1Tax;
    private int printert2; private int printert2Price = 1000; private long printert2Tax;
    private int printert3; private int printert3Price = 10000; private long printert3Tax;
    private int printert4; private int printert4Price = 100000; private long printert4Tax;
    private int printert5; private int printert5Price = 1000000; private long printert5Tax;
    private int printert6; private long printert6Price = 100000000; private long printert6Tax;
    private int printert7; private long printert7Price = 10000000000L; private long printert7Tax;
    private int printert8; private long printert8Price = 1000000000000L; private long printert8Tax;
    private int printert9; private long printert9Price = 100000000000000L; private long printert9Tax;
    private int printert10; private long printert10Price = 10000000000000000L; private long printert10Tax;
    private int printert11; private long printert11Price = 1000000000000000000L; private long printert11Tax; //type long too long :-D
    long printersTax;
    private int time;
    private int printerSpeed = 1000;
    long tax;
    int taxAmount = 20;
    long taxLabel;
    long taxTime = 20000;
    long billtime = 60000;
    long click = 1;
    long printerpower;
    long printerpowerlabel;
    long Wpower;
    long WpowerPay;
    long WpowerPayI;
    public double odmena;
    double odmenalabel;

    // String Section
    public static final String SAVE = "Clicker%Game%2%Save";

    // Ad Section
    private InterstitialAd RandomInterstitialAd;
    private RewardedVideoAd RandomMoneyAd;

    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exp) {
            exp.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    /*public boolean onOptionsItemSelected(MenuItem item) {
        Intent gotoSettings = new Intent(this, ActionSettingsActivity.class);
        getIntent().putExtra("money", money);
        getIntent().putExtra("powerBillEnabled", powerBillEnabled);
        getIntent().putExtra("taxEnabled", taxEnabled);
        getIntent().putExtra("billtime", billtime);
        getIntent().putExtra("taxTime", taxTime);
        getIntent().putExtra("taxAmount", taxAmount);
        startActivity(gotoSettings);
        return super.onOptionsItemSelected(item);
    }*/

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //AutoSave Section
        SharedPreferences saveGame = getSharedPreferences(SAVE, MODE_PRIVATE);
        final SharedPreferences.Editor save = saveGame.edit();

        SharedPreferences loadGame = getSharedPreferences(SAVE, MODE_PRIVATE);
        money = loadGame.getLong("money", 0);
        click = loadGame.getLong("click", click);
        printerpower = loadGame.getLong("printerpower", 0);
        printers = loadGame.getInt("printers", 0);
        printert1 = loadGame.getInt("printert1", 0);
        printert2 = loadGame.getInt("printert2", 0);
        printert3 = loadGame.getInt("printert3", 0);
        printert4 = loadGame.getInt("printert4", 0);
        printert5 = loadGame.getInt("printert5", 0);
        printert6 = loadGame.getInt("printert6", 0);
        printert7 = loadGame.getInt("printert7", 0);
        printert8 = loadGame.getInt("printert8", 0);
        printert9 = loadGame.getInt("printert9", 0);
        printert10 = loadGame.getInt("printert10", 0);
        printert11 = loadGame.getInt("printert11", 0);
        time = loadGame.getInt("time", 0);
        printerSpeed = loadGame.getInt("printerSpeed", printerSpeed);
        Wpower = loadGame.getLong("Wpower", Wpower);
        WpowerPay = loadGame.getLong("WpowerPay", WpowerPay);
        WpowerPayI = loadGame.getLong("WpowerPayI", WpowerPayI);
        taxAmount = loadGame.getInt("taxAmount", taxAmount);
        powerBillEnabled = loadGame.getBoolean("powerBillEnabled", powerBillEnabled);
        taxEnabled = loadGame.getBoolean("taxEnabled", taxEnabled);

        // Ads Section
        MobileAds.initialize(this, "ca-app-pub-9086446979210331~1649262169");
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713"); //!!!!!!!!!!! TEST ID !!!!!!!!!!!
        prepareAd();

        RandomMoneyAd = MobileAds.getRewardedVideoAdInstance(this);
        RandomMoneyAd.setRewardedVideoAdListener(this);

        java.util.concurrent.ScheduledExecutorService scheduler =
                java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                android.util.Log.i("hello", "world");
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (RandomMoneyAd.isLoaded()) {
                        } else {
                            //RandomMoneyAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build()); //!!!!!!!!!!! TEST ID !!!!!!!!!!!
                            RandomMoneyAd.loadAd("ca-app-pub-9086446979210331/4874191306", new AdRequest.Builder().build());
                            android.util.Log.d("TAG", " Interstitial not loaded");
                            prepareAd();
                        }
                        prepareAd();
                    }
                });
            }
        }, 5, 120, java.util.concurrent.TimeUnit.SECONDS);

        // Toasts
        final Context context = getApplicationContext();
        final CharSequence text = "Tax: The Game " + "(" + version + ")" + " Started!";
        final int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        //toast.show();

        // JLabels
        final TextView cash = findViewById(R.id.cash);
        final TextView overclockedspeed = findViewById(R.id.overclockedspeed);
        final TextView machines = findViewById(R.id.machines);
        final TextView gameclock = findViewById(R.id.gameclock);
        final TextView taxview = findViewById(R.id.taxview);
        final TextView powerusage = findViewById(R.id.powerusage);
        final TextView powerview = findViewById(R.id.powerusage);

        // Buttons Top
        final Button cashbutton = findViewById(R.id.cashbutton);
        final Button overclock = findViewById(R.id.overclockPrinters);
        final Button plusclickbutton = findViewById(R.id.plusclick);
        final Button adbutton = findViewById(R.id.adbutton);
        final Button shop = findViewById(R.id.shop);
        final Button btn_reset = findViewById(R.id.btn_reset);

        // Buttons Middle
        final Button machine = findViewById(R.id.machineT1);
        final Button machine1 = findViewById(R.id.machineT2);
        final Button machine2 = findViewById(R.id.machineT3);
        final Button machine3 = findViewById(R.id.machineT4);
        final Button machine4 = findViewById(R.id.machineT5);
        final Button machine5 = findViewById(R.id.machineT6);
        final Button machine6 = findViewById(R.id.machineT7);
        final Button machine7 = findViewById(R.id.machineT8);
        final Button machine8 = findViewById(R.id.machineT9);
        final Button machine9 = findViewById(R.id.machineT10);
        final Button machine10 = findViewById(R.id.machineT11);

        // Buttons Bottom
        final Button btn_about = findViewById(R.id.btn_about);

        // Timers
        Timer clock = new Timer();
        Timer taxtimer = new Timer();
        Timer autosave = new Timer();
        final Timer powerbill = new Timer();

        Thread syncthread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(125);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (money > 1000000) {
                                    moneyLabel = money / 1000000;
                                    printerpowerlabel = printerpower / 1000000;
                                    cash.setText("Money: " + moneyLabel + "mil€ " + "| " + printerpowerlabel + "mil€" + "/" + printerSpeed + "s");
                                } else if (money < 1000000) {
                                    cash.setText("Money: " + money + "€ " + "| " + printerpower + "€" + "/" + printerSpeed + "ms");
                                }
                                machines.setText("Printers Active: " + printers);
                                overclockedspeed.setText("Printer Clock: " + printerSpeed + "ms");
                                gameclock.setText("You have played for: " + time + "min");

                                taxview.setText("Tax every: " + taxTime / 1000 + "sec");
                                powerview.setText("Bills every: " + billtime + "sec");
                                powerusage.setText("Power Consumption: " + Wpower + "W");

                                if (printerSpeed > 751) {
                                    overclock.setText("OC Printers (" + (printerSpeed - 1000) + "ms)" + "\n 5000€");
                                } else if (printerSpeed < 751) {
                                    overclock.setText("OC Printers " + "(MAX)");
                                }

                                if (tax > 1000000) {
                                    taxLabel = tax / 1000000;
                                    Toast.makeText(context, "Taxes & Printer Taxes paid!" + "\nPaid: " + taxLabel + "mil€", duration).show();
                                    money -= tax;
                                    tax -= tax;
                                } else if (tax > 0) {
                                    Toast.makeText(context, "Taxes & Printer Taxes paid!" + "\nPaid: " + tax + "€", duration).show();
                                    money -= tax;
                                    tax -= tax;
                                }

                                if (odmena > 1000000) {
                                    odmenalabel = odmena / 1000000;
                                    Toast.makeText(context, "You have been awarded: " + odmenalabel + "mil€", duration).show();
                                    money += odmena;
                                    odmena -= odmena;
                                } else if (odmena > 0) {
                                    Toast.makeText(context, "You have been awarded: " + odmena + "€", duration).show();
                                    money += odmena;
                                    odmena -= odmena;
                                }

                                if (WpowerPay > 1000000) {
                                    long WpowerPayLabel = WpowerPay / 1000000;
                                    Toast.makeText(context, "Power Bill paid!" + "\nPaid: " + WpowerPayLabel + "mil€", duration).show();
                                    money -= WpowerPay / 2;
                                    WpowerPay = 0;
                                } else if (WpowerPay < 1000000 && WpowerPay > 0) {
                                    Toast.makeText(context, "Power Bill paid!" + "\nPaid: " + WpowerPay + "€", duration).show();
                                    money -= WpowerPay / 2;
                                    WpowerPay = 0;
                                }

                                cashbutton.setText("Click\n" + click + "€");
                                machine.setText("Tier 1 " + "(" + printert1 + ")" + "\n100€\n+2€/s");
                                machine1.setText("Tier 2 " + "(" + printert2 + ")" + "\n1000€\n+25€/s");
                                machine2.setText("Tier 3 " + "(" + printert3 + ")" + "\n10000€\n+250€/s");
                                machine3.setText("Tier 4 " + "(" + printert4 + ")" + "\n100000€\n+2500€/s");
                                machine4.setText("Tier 5 " + "(" + printert5 + ")" + "\n1 Million€\n+25000€/s");
                                machine5.setText("Tier 6 " + "(" + printert6 + ")" + "\n100 Million€\n+25 Million/s");
                                machine6.setText("Tier 7 " + "(" + printert7 + ")" + "\n10 Billion€\n+250 Million/s");
                                machine7.setText("Tier 8 " + "(" + printert8 + ")" + "\n1 Trillion€\n+25 Billion/s");
                                machine8.setText("Tier 9 " + "(" + printert9 + ")" + "\n100 Trillion€\n+2,5 Trillion/s");
                                machine9.setText("Tier 10 " + "(" + printert10 + ")" + "\n10 Quadrillion€\n+250 Trillion/s");
                                machine10.setText("Tier 11 " + "(" + printert11 + ")" + "\n1 Quintillion€\n+25 Quadrillion/s");
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        syncthread.start();

        autosave.schedule(new TimerTask() {
            @Override
            public void run() {
                save.putLong("money", money);
                save.putInt("printers", printers);
                save.putInt("printert1", printert1);
                save.putInt("printert2", printert2);
                save.putInt("printert3", printert3);
                save.putInt("printert4", printert4);
                save.putInt("printert5", printert5);
                save.putInt("printert6", printert6);
                save.putInt("printert7", printert7);
                save.putInt("printert8", printert8);
                save.putInt("printert9", printert9);
                save.putInt("printert10", printert10);
                save.putInt("printert11", printert11);
                save.putInt("time", time);
                save.putInt("printerSpeed", printerSpeed);
                save.putLong("click", click);
                save.putLong("printerpower", printerpower);
                save.putLong("Wpower", Wpower);
                save.putLong("WpowerPay", WpowerPay);
                save.putLong("WpowerPayI", WpowerPayI);
                save.putInt("taxAmount", taxAmount);
                save.putBoolean("powerBillEnabled", powerBillEnabled);
                save.putBoolean("taxEnabled", taxEnabled);
                save.apply();
            }
        }, 5000, 5000);

        clock.schedule(
                new TimerTask() {
                    public void run() {
                        time += 1;
                    }
                }, 60000, 60000);

        if (taxEnabled) {
            taxtimer.schedule(new TimerTask() {
                public void run() {
                    printert1Tax = printert1Price / 100 * taxAmount;
                    printert2Tax = printert2Price / 100 * taxAmount;
                    printert3Tax = printert3Price / 100 * taxAmount;
                    printert4Tax = printert4Price / 100 * taxAmount;
                    printert5Tax = printert5Price / 100 * taxAmount;
                    printert6Tax = printert6Price / 100 * taxAmount;
                    printert7Tax = printert7Price / 100 * taxAmount;
                    printert8Tax = printert8Price / 100 * taxAmount;
                    printert9Tax = printert9Price / 100 * taxAmount;
                    printert10Tax = printert10Price / 100 * taxAmount;
                    printert11Tax = printert11Price / 100 * taxAmount;
                    printersTax = printert1Tax + printert2Tax + printert3Tax + printert4Tax + printert5Tax + printert6Tax + printert7Tax + printert8Tax + printert9Tax + printert10Tax + printert11Tax;
                    tax = money / 100 * taxAmount;
                    printersTax += tax;
                }
            }, taxTime, taxTime);
        }

        if (powerBillEnabled) {
            powerbill.schedule(new TimerTask() {
                public void run() {
                    WpowerPay += WpowerPayI;
                }
            }, billtime, billtime);
        }

        cashbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                money += click;
            }
        });

        plusclickbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 50) {
                    money -= 50;
                    click++;
                } else if (50 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        overclock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 5000 & printerSpeed > 751) {
                    money -= 5000;
                    printerSpeed -= 5;
                    Toast.makeText(context, "Printers Successfully Overclocked!", duration).show();
                } else if (5000 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                } else if (printerSpeed < 751) {
                    Toast.makeText(context, "Maximum Overclock Reached!", duration).show();
                }
            }
        });

        /*shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopIntent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(shopIntent);
            }
        });*/

        machine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 100) {
                    Toast.makeText(context, "Printer Bought!", duration).show();
                    money -= 100;
                    printers += 1;
                    printert1 += 1;
                    printerpower += 2;
                    Wpower += 2;
                } else if (100 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        machine1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 1000) {
                    Toast.makeText(context, "Printer Bought!", duration).show();
                    money -= 1000;
                    printers += 1;
                    printert2 += 1;
                    printerpower += 25;
                    Wpower += 5;
                } else if (1000 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        machine2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 10000) {
                    Toast.makeText(context, "Printer Bought!", duration).show();
                    money -= 10000;
                    printers += 1;
                    printert3 += 1;
                    printerpower += 250;
                    Wpower += 10;
                } else if (10000 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        machine3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 100000) {
                    Toast.makeText(context, "Printer Bought!", duration).show();
                    money -= 100000;
                    printers += 1;
                    printert4 += 1;
                    printerpower += 2500;
                    Wpower += 40;
                } else if (100000 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        machine4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 1000000) {
                    Toast.makeText(context, "Printer Bought!", duration).show();
                    money -= 1000000;
                    printers += 1;
                    printert5 += 1;
                    printerpower += 25000;
                    Wpower += 145;
                } else if (1000000 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        machine5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 100000000) { // "\n100 Million€\n+25 Million/s");
                    Toast.makeText(context, "Printer Bought!", duration).show();
                    money -= 100000000;
                    printers += 1;
                    printert6 += 1;
                    printerpower += 25000000;
                    Wpower += 200;
                } else if (100000000 >= money) {
                    Toast.makeText(context, "Not Enough Money!", duration).show();
                }
            }
        });

        machine6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 10000000000L) { // "\n10 Billion€\n+250 Million/s");
                    Toast.makeText(context, "Printer bought!", duration).show();
                    money -= 10000000000L; //10000000000L;
                    printers += 1;
                    printert7 += 1;
                    printerpower += 250000000;
                    Wpower += 250;
                } else if (10000000000L >= money) {
                    Toast.makeText(context, "Not enough money!", duration).show();
                }
            }
        });

        machine7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 1000000000000L) { // "\n1 Trillion€\n+25 Billion/s");
                    Toast.makeText(context, "Printer bought!", duration).show();
                    money -= 1000000000000L;
                    printers += 1;
                    printert8 += 1;
                    printerpower += 25000000000L;
                    Wpower += 250;
                } else if (1000000000000L >= money) {
                    Toast.makeText(context, "Not enough money!", duration).show();
                }
            }
        });

        machine8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 100000000000000L) { // "\n100 Trillion€\n+2,5 Trillion/s");
                    Toast.makeText(context, "Printer bought!", duration).show();
                    money -= 100000000000000L;
                    printers += 1;
                    printert9 += 1;
                    printerpower += 2500000000000L;
                    Wpower += 250;
                } else if (100000000000000L >= money) {
                    Toast.makeText(context, "Not enough money!", duration).show();
                }
            }
        });

        machine9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 10000000000000000L) { // "\n10 Quadrillion€\n+250 Trillion/s");
                    Toast.makeText(context, "Printer bought!", duration).show();
                    money -= 10000000000000000L;
                    printers += 1;
                    printert10 += 1;
                    printerpower += 250000000000000L;
                    Wpower += 250;
                } else if (10000000000000000L >= money) {
                    Toast.makeText(context, "Not enough money!", duration).show();
                }
            }
        });

        machine10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (money >= 1000000000000000000L) { // "\n1 Quintillion€\n+25 Quadrillion/s");
                    Toast.makeText(context, "Printer bought!", duration).show();
                    money -= 1000000000000000000L;
                    printers += 1;
                    printert11 += 1;
                    printerpower += 25000000000000000L;
                    Wpower += 250;
                } else if (1000000000000000000L >= money) {
                    Toast.makeText(context, "Not enough money!", duration).show();
                }
            }
        });


        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RandomMoneyAd.isLoaded()) {
                    RandomMoneyAd.show();
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        Thread printercompute = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    delay(printerSpeed);
                    money += printerpower;
                }
            }
        });
        printercompute.start();

        Thread powercompute = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    delay(printerSpeed);
                    WpowerPayI += Wpower;
                }
            }
        });

        powercompute.start();

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareAd();
                RandomInterstitialAd.show();
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
    }

    public void prepareAd() {
        RandomInterstitialAd = new InterstitialAd(this);
        RandomInterstitialAd.setAdUnitId("ca-app-pub-9086446979210331/4885707704");
        RandomInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void run() {

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        if (money < 0) {
            money += 500;
        }
        else {
            odmena = money * 1.5;
        }

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
