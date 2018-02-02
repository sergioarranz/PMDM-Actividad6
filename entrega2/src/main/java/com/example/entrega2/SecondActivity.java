package com.example.entrega2;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mylib.GPSAdmin.GPSTracker;
import com.example.mylib.asynctasks.HttpAsyncTask;
import com.example.mylib.asynctasks.HttpJsonAsyncTask;
import com.example.mylib.fragment.DetallesFragment;
import com.example.mylib.fragment.ListFragment;
import com.google.android.gms.maps.SupportMapFragment;

public class SecondActivity extends AppCompatActivity {
    SecondActivityEvents events;
    ListFragment listFragment;
    SupportMapFragment mapFragment;
    DetallesFragment detallesFragment;
    //private LinearLayout llContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        events = new SecondActivityEvents(this);
        DataHolder.MyDataHolder.getFirebaseAdmin().setFirebaseAdminListener(events);
        /*
        Para no perder la referencia a firebaseAdmin dado que de un activity a otro todo pasa a null,
        entonces lo guardamos en el dataHolder y se desde el second activity lo llamamos y decimos que sobrescriba
        los eventos que escucha dado qeu ahora escuchará los del second activity.
         */

        this.listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentList);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMapa);
        mapFragment.getMapAsync(events);
        detallesFragment = (DetallesFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMapDetail);
        /*
        Para añadir de forma dinámica fragments a un layout y luego trabajar con ellos para poder destruirlos
        si queremos o conservarlos vamos a realizar los siguientes pasos:
         */
        //this.llContainer = this.findViewById(R.id.llContainer); // sobre el LinearLayout vamos a insetar los fragments

        //this.listFragment= new ListFragment(); // Uno de los fragments a insertar


        //El fragmentManager se encarga de fgestionar los fragmentos para poder insertarlos en el linearLayout o sacarlos.

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //Con la operación add especificamos el contenedor donde queremos meter el fragment, el fragment que queremos meter y un identificador para es fragment
        //transaction.add(this.getLlContainer().getId(), this.getListFragment(), "fragmentList");
        transaction.hide(listFragment);
        transaction.hide(detallesFragment);
        transaction.show(mapFragment);
        transaction.commit(); // comiteamos

        GPSTracker gpsTracker = new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            Log.v("SecondActivity", gpsTracker.getLatitude()+" "+gpsTracker.getLongitude());
        } else {
               gpsTracker.showSettingsAlert();
        }

        /*HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        httpAsyncTask.execute("https://raw.githubusercontent.com/ubelab/mockandroidasynctask/master/MockAndroidAsyncTask/images/mockasynctaskandroid.png",
                "http://iosdevlog.com/assets/images/Rx/RxJava/AsynchronousAndroidProgramming-SecondEdition/AsyncTask.png",
                "https://i.stack.imgur.com/3cEZG.jpg",
                "https://i.stack.imgur.com/5sv7T.png",
                "https://jamesdalyburton.files.wordpress.com/2014/02/screen-shot-2014-02-17-at-1-09-03-pm.jpg");*/

        HttpJsonAsyncTask httpJsonAsyncTask = new HttpJsonAsyncTask();
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?id=%s&appid=%s",
                "3117732", DataHolder.MyDataHolder.API_KEY);
        Log.v("HttpJsonAsyncTask",url);
        httpJsonAsyncTask.execute(url);

        HttpJsonAsyncTask httpJsonAsyncTask1=new HttpJsonAsyncTask();
        String url1=String.format("http://10.0.2.2/pruebasJSON/leejugadores.php");
        httpJsonAsyncTask.setHttpJsonAsyncTaskListener(events);
        httpJsonAsyncTask1.execute(url1);

        //Log.v("SecondActivity","--------EMAAAIL: "+DataHolder.MyDataHolder.firebaseAdmin.user.getEmail());
    /*
    Hay que tener en cuenta que si añadimos otro fragment al linearLayout este último sobrescribirá al primero por tanto para poder
    meter varios, lo mejor es crear distintos linearlayouts dentro un linearlayout padre.
    Cada linear layout hijo contendrá un fragment.
     */


       /* ArrayList<String> contenidoLista = new ArrayList<>(); // este array lo creamos de forma manual, pero a posteriori lo que haremos es descargarlo de firebase
        contenidoLista.add("Yony");
        contenidoLista.add("Javier");
        contenidoLista.add("Ramsés");
        contenidoLista.add("Sergio");
        contenidoLista.add("Oscar");
        contenidoLista.add("Manuel");
        contenidoLista.add("Taysir");
       this.listFragment.getMyLista().setAdapter(new ListAdapter(contenidoLista)); // pasamos por parámetro el arrayList creado para inicializar el arrayList del listAdapter
        */
    }

    public SecondActivityEvents getEvents() {
        return events;
    }

    public void setEvents(SecondActivityEvents events) {
        this.events = events;
    }


    public ListFragment getListFragment() {
        return listFragment;
    }

    public void setListFragment(ListFragment listFragment) {
        this.listFragment = listFragment;
    }
/*
    public LinearLayout getLlContainer() {
        return llContainer;
    }

    public void setLlContainer(LinearLayout llContainer) {
        this.llContainer = llContainer;
    }
    */
}
