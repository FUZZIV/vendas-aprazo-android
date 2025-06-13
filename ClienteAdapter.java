public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
    private List<Cliente> clientes;
    private OnClienteClickListener listener;

    public interface OnClienteClickListener {
        void onClienteClick(Cliente cliente);
    }

    public ClienteAdapter(List<Cliente> clientes, OnClienteClickListener listener) {
        this.clientes = clientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = clientes.get(position);
        holder.tvNome.setText(cliente.getNome());
        holder.tvTelefone.setText(cliente.getTelefone());
        
        holder.itemView.setOnClickListener(v -> listener.onClienteClick(cliente));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        notifyDataSetChanged();
    }

    static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvTelefone;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeCliente);
            tvTelefone = itemView.findViewById(R.id.tvTelefoneCliente);
        }
    }
}
