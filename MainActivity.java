public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClientes = findViewById(R.id.btnClientes);
        Button btnClientesPendentes = findViewById(R.id.btnClientesPendentes);

        btnClientes.setOnClickListener(v -> {
            startActivity(new Intent(this, ListaClientesActivity.class));
        });

        btnClientesPendentes.setOnClickListener(v -> {
            startActivity(new Intent(this, ClientesPendentesActivity.class));
        });
    }
}
