package com.example.botonesinferiores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botonesinferiores.models.Base;

import java.util.ArrayList;
import java.util.List;

public class BasesAdapter extends RecyclerView.Adapter<BasesAdapter.ViewHolder> {

    // Lista de objetos Base que se mostrarán en el RecyclerView
    List<Base> listaBases;

    // Interfaz para manejar clics en elementos del RecyclerView
    public OnItemClicked onClick;

    // Constructor que recibe la lista de bases y la interfaz OnItemClicked
    public BasesAdapter(List<Base> listaBases, OnItemClicked onClick) {
        this.listaBases = listaBases;
        this.onClick = onClick;
    }

    // Método para inflar el diseño de cada elemento del RecyclerView
    @NonNull
    @Override
    public BasesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_datos, parent, false);
        return new ViewHolder(vista);
    }

    // Método para asignar datos a los elementos de la vista en cada posición
    @Override
    public void onBindViewHolder(@NonNull BasesAdapter.ViewHolder holder, int position) {
        Base base = listaBases.get(position);
        holder.tvNomUsuario.setText(base.nomUsuario.toUpperCase());
        holder.tvHoraInicio.setText(base.horaInicioUsuario.toUpperCase());
        holder.tvHoraFin.setText(base.horaFinUsuario.toUpperCase());
        holder.tvPeso.setText(base.pesoUsuario.toUpperCase());
        holder.tvNota.setText(base.notaUsuario.toUpperCase());

        holder.tvTipoEjercicio.setText(base.ejercicioUsuario.toUpperCase());
        holder.tvPesoCarga.setText(base.pesoacargarUsuario.toUpperCase());
        holder.tvRepeticiones.setText(base.repeticionesUsuario.toUpperCase());
        holder.tvNumeroSeries.setText(base.serieUsuario.toUpperCase());

        /*holder.tvNombrePerfil.setText(base.nombrePerfil.toUpperCase());
        holder.tvApellidosPerfil.setText(base.apellidosPerfil.toUpperCase());
        holder.tvCorreoPerfil.setText(base.correoPerfil.toUpperCase());
        holder.tvEdadPerfil.setText(base.edadPerfil.toUpperCase());
        holder.tvPesoInicialPerfil.setText(base.pesoInicialPerfil.toUpperCase());
        holder.tvMotivacionPerfil.setText(base.motivacionPerfil.toUpperCase());*/

        // Manejar clics en los botones de editar y borrar
        holder.ibtnEditar.setOnClickListener(v -> {
            onClick.editarR(base);
        });
        holder.ibtnBorrar.setOnClickListener(v -> {
            onClick.eliminarR(base);
        });
    }

    // Método para obtener la cantidad total de elementos en el RecyclerView
    @Override
    public int getItemCount() {
        return listaBases.size();
    }

    // Clase interna ViewHolder que contiene referencias a los elementos de la vista
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomUsuario, tvHoraInicio, tvHoraFin, tvPeso, tvNota,tvTipoEjercicio,tvPesoCarga,tvRepeticiones,tvNumeroSeries,
                tvNombrePerfil,tvApellidosPerfil,tvCorreoPerfil,tvEdadPerfil,tvPesoInicialPerfil,tvMotivacionPerfil;
        ImageButton ibtnEditar, ibtnBorrar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Asignar referencias a los elementos de la vista
            tvNomUsuario = itemView.findViewById(R.id.tvNomUsuario);
            tvHoraInicio = itemView.findViewById(R.id.tvHoraInicio);
            tvHoraFin = itemView.findViewById(R.id.tvHoraFin);
            tvPeso = itemView.findViewById(R.id.tvPeso);
            tvNota = itemView.findViewById(R.id.tvNota);
            ibtnBorrar = itemView.findViewById(R.id.btnBorrar);
            ibtnEditar = itemView.findViewById(R.id.btnEditar);

            tvTipoEjercicio = itemView.findViewById(R.id.tvTipoE);
            tvPesoCarga = itemView.findViewById(R.id.tvPesoCarga);
            tvRepeticiones = itemView.findViewById(R.id.tvRepeticiones);
            tvNumeroSeries = itemView.findViewById(R.id.tvSeries);
            /*tvNombrePerfil = itemView.findViewById(R.id.TvwnombreP);
            tvApellidosPerfil = itemView.findViewById(R.id.Papellidos);
            tvCorreoPerfil = itemView.findViewById(R.id.TvcorreoP);
            tvEdadPerfil = itemView.findViewById(R.id.TvedadP);
            tvPesoInicialPerfil = itemView.findViewById(R.id.TvpesoIP);
            tvMotivacionPerfil = itemView.findViewById(R.id.tvMotivacionP);*/
        }
    }

    // Interfaz para manejar clics en elementos del RecyclerView
    public interface OnItemClicked {
        void editarR(Base base);

        void eliminarR(Base base);
    }

    // Método para filtrar la lista de bases y actualizar el RecyclerView
    public void filtrarUsuario(List<Base> listaFil) {
        this.listaBases = listaFil;
        notifyDataSetChanged();
    }
}
