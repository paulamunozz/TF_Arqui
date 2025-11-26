import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';
import {MatTableDataSource} from '@angular/material/table';
import {Comentario} from '../../model/reportes/comentario';
import {EventoXVecinoService} from '../../services/evento-x-vecino-service';
import {EventoXVecino} from '../../model/evento-x-vecino';
import {ChartDataset, ChartOptions} from 'chart.js';
import { BaseChartDirective, provideCharts, withDefaultRegisterables } from 'ng2-charts';
import {MatIconModule} from '@angular/material/icon';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-us21-us22-us23-municipalidad-detalle-evento',
  imports: [
    BaseChartDirective,
    MatIconModule,
    MatCard,
    MatCardContent,
    MatButton,

  ],
  templateUrl: './municipalidad-detalle-evento.html',
  styleUrl: './municipalidad-detalle-evento.css',
  standalone: true,
  providers: [provideCharts(withDefaultRegisterables())],
})
export class MunicipalidadDetalleEvento {
  private eventoService: EventoService = inject(EventoService);
  private exvService: EventoXVecinoService = inject(EventoXVecinoService);
  private router = inject(Router);

  evento: Evento = new Evento();
  comentarios:MatTableDataSource<Comentario> = new MatTableDataSource<Comentario>();

  hasData = false;
  opcionesGraficoGenero: ChartOptions = {
    responsive: true,
    plugins: {
      legend:{position: 'top'},
      title: {
        display: true,
        text: 'Cantidad de vecinos por género',
        font:{size: 20}
      }
    }
  }
  opcionesGraficoEdad: ChartOptions = {
    responsive: true,
    plugins: {
      legend:{position: 'top'},
      title: {
        display: true,
        text: 'Cantidad de vecinos por edad',
        font:{size: 20}
      }
    }
  }
  labelsGraficoGenero= ['Mujeres', 'Hombres'];
  labelsGraficoEdad= ['15-24', '25-34', '35-44', '45-54', '55-64', '65+'];
  dataGraficoGenero: ChartDataset[];
  dataGraficoEdad: ChartDataset[];

  id:number;
  constructor(private route:ActivatedRoute) { }
  ngOnInit(){
    this.route.params.subscribe(params => {
      this.id = +params['id'];

      this.eventoService.detalle(this.id).subscribe({
        next: (data) => {
          this.evento = data;
          console.log("Evento cargado: ", data);
        },
        error: (error) => {
          console.log(error);
        }
      })
    })

    this.listarComentarios()
    this.mostrarGraficos()
  }

  editarEvento(){
    console.log('ID a editar:', this.id);
    this.router.navigate(['/modificar-evento', this.id]);
  }

  eliminar(id:number){
    this.eventoService.eliminar(id).subscribe({
      next: (data) => {
        console.log(data);
        this.router.navigate(['/eventos']);
      }
    })
  }

  popUpVisible = false;
  mostrarPopUp()
  {
    this.popUpVisible = true;
  }
  cerrarPopUp(){
    this.popUpVisible = false;
  }

  listarComentarios(){
    this.exvService.comentarios(this.id).subscribe({
      next: (data) => {
        this.comentarios.data = data;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  eliminarComentario(idEXV:number){
    let exv = new EventoXVecino();
    exv.id = idEXV;
    exv.comentario = null;
    this.exvService.modificar(exv).subscribe({
      next: (data) => {
        this.listarComentarios();
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  mostrarGraficos() {
    this.exvService.estadisticasVecinosPorEvento(this.id).subscribe({
      next: (data) => {
        if (data) {
          this.hasData = true;

          this.dataGraficoGenero = [{
            data: [
              data.mujeres,
              data.hombres
            ],
            label: 'Cantidad de vecinos',
            backgroundColor: [
              '#ff31a9',
              '#5c55ff'
            ]
            }]

          this.dataGraficoEdad = [{
            data: [
              data._15_24_,
              data._25_34_,
              data._35_44_,
              data._45_54_,
              data._55_64_,
              data._65_mas_
            ],
            label: 'Cantidad de vecinos',
            backgroundColor: [
              '#ff2222',
              '#ff9422',
              '#fff822',
              '#4fff23',
              '#22ffcb',
              '#b623ff',
            ]
            }];
        } else {
          this.hasData = false;
        }
      }
    });
  }
}
