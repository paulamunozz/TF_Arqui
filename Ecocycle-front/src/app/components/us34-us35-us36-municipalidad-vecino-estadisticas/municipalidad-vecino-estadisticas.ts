import {Component, inject} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {EventoService} from '../../services/evento-service';
import {FormsModule} from '@angular/forms';
import {CantidadEventosLogradosDTO} from '../../model/reportes/cantidad-eventos-logrados-dto';
import {ReciclajeService} from '../../services/reciclaje-service';
import {BaseChartDirective, provideCharts, withDefaultRegisterables} from 'ng2-charts';
import {ChartDataset, ChartOptions} from 'chart.js';

@Component({
  selector: 'app-us34-us35-us36-municipalidad-vecino-estadisticas',
  imports: [
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    FormsModule,
    BaseChartDirective
  ],
  templateUrl: './municipalidad-vecino-estadisticas.html',
  styleUrl: './municipalidad-vecino-estadisticas.css',
  providers: [provideCharts(withDefaultRegisterables())],
})
export class MunicipalidadVecinoEstadisticas {
  private eventoService: EventoService = inject(EventoService);
  private reciclajeService: ReciclajeService = inject(ReciclajeService);

  cantidadEventos: CantidadEventosLogradosDTO = new CantidadEventosLogradosDTO();
  distrito: string = '';

  eventosPieChartLabels = ['Eventos logrados', 'Eventos sin lograr'];
  eventosPieChartDataset : ChartDataset[];
  eventosPieChartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend:{position: 'top'}
    }
  }

  dualAxisChartData: { labels: string[], datasets: ChartDataset[] };
  dualAxisChartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: { position: 'top' },
      tooltip: { mode: 'index', intersect: false }
    },
    scales: {
      y: {
        type: 'linear',
        position: 'left',
        title: { display: true, text: 'Cantidad de registros' }
      },
      y1: {
        type: 'linear',
        position: 'right',
        title: { display: true, text: 'Peso total (kg)' },
        grid: { drawOnChartArea: false }
      }
    }
  };

  ngOnInit() {
    this.filtrarDistrito()
  }

  filtrarDistrito(){
    this.eventoService.cantidadEventosLogrados(this.distrito).subscribe({
      next: (data) => {
        this.cantidadEventos=data;
        console.log(this.cantidadEventos);
      },
      error: error => {
        console.log(error);
      }
    })

    this.reciclajeService.cantidadPorTipoResiduo(this.distrito).subscribe({
      next: (data) => {
        this.generarDualAxisChart(data);
      },
      error: (err) => console.log(err)
    });

    this.eventoService.cantidadEventosLogrados(this.distrito).subscribe({
      next: (data) => {
        this.mostrarEventosPieChart(data);
      },
      error: (err) => console.log(err)
    })
  }

  mostrarEventosPieChart(data:CantidadEventosLogradosDTO){
    this.eventosPieChartDataset = [{
      data: [
        data.cantidadEventosLogrados,
        data.cantidadEventosSinLograr
      ],
      label: 'Cantidad de eventos',
      backgroundColor:[
        '#0abc7f',
        '#ffbc22'
      ]
    }]
  }

  generarDualAxisChart(data: any[]) {
    this.dualAxisChartData = {
      labels: data.map(d => d.tipo),
      datasets: [
        {
          type: 'line',
          label: 'Peso total (kg)',
          data: data.map(d => d.peso),
          borderColor: '#8106cc',
          yAxisID: 'y1'
        },
        {
          type: 'bar',
          label: 'Cantidad de registros',
          data: data.map(d => d.cantidad),
          backgroundColor: '#69bddd',
        },
      ]
    };
  }
}
