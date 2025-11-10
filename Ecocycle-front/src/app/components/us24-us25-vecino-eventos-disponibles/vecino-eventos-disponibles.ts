import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {DateAdapter, MAT_DATE_LOCALE, MatNativeDateModule, MatOption} from '@angular/material/core';
import {DatePipe} from '@angular/common';
import {EventoService} from '../../services/evento-service';
import {MatTableDataSource} from '@angular/material/table';
import {Evento} from '../../model/evento';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {MatFormField, MatSuffix} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';
import {EventoXVecino} from '../../model/evento-x-vecino';
import {EventoXVecinoService} from '../../services/evento-x-vecino-service';

@Component({
  selector: 'app-us24-us25-vecino-eventos-disponibles',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    MatDatepicker,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    MatSuffix,
    MatNativeDateModule,
    DatePipe,
  ],
  templateUrl: './vecino-eventos-disponibles.html',
  styleUrl: './vecino-eventos-disponibles.css',
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-PE' },
    DatePipe
  ]
})
export class VecinoEventosDisponibles {
  formFiltro: FormGroup;
  private fb = inject(FormBuilder);
  private eventoService: EventoService = inject(EventoService);
  private exvService: EventoXVecinoService = inject(EventoXVecinoService);
  private router : Router = inject(Router);
  private datePipe= inject(DatePipe);

  constructor(private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('es-PE');

    this.formFiltro = this.fb.group({
      distrito:[''],
      nombre: [''],
      tipo: [''],
      metodo: [''],
      fechaInicio: [''],
      fechaFin: ['']
    });
  }

  eventos: MatTableDataSource<Evento> = new MatTableDataSource<Evento>();

  ngOnInit() {
    this.listarEventos();
  }

  listarEventos() {
    const filtros = {...this.formFiltro.value}

    if (filtros.fechaInicio) {
      filtros.fechaInicio = this.datePipe.transform(filtros.fechaInicio, 'yyyy-MM-dd');
    }
    if (filtros.fechaFin) {
      filtros.fechaFin = this.datePipe.transform(filtros.fechaFin, 'yyyy-MM-dd');
    }

    console.log(filtros);
    this.eventoService.listarPorDistrito(filtros).subscribe({

      next: (data) => {
        this.eventos.data = data;
        console.log("Eventos cargados: ", data);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  unirseEvento(eventoId:number){
    let exv = new EventoXVecino()
    exv.eventoId = eventoId;
    exv.vecinoId = 1;

    this.exvService.registrar(exv).subscribe({
      next: (data) => {
        console.log(data);
        this.router.navigate(['/mis-eventos/' + eventoId]);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
