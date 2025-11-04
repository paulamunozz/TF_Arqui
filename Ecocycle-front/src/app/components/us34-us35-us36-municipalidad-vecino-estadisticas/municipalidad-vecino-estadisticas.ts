import {Component, inject} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {EventoService} from '../../services/evento-service';
import {FormsModule} from '@angular/forms';
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {CantidadEventosLogradosDTO} from '../../model/reportes/cantidad-eventos-logrados-dto';
import {CantidadResiduosDTO} from '../../model/reportes/cantidad-residuos-dto';
import {ReciclajeService} from '../../services/reciclaje-service';

@Component({
  selector: 'app-us34-us35-us36-municipalidad-vecino-estadisticas',
  imports: [
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    FormsModule,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderRowDef
  ],
  templateUrl: './municipalidad-vecino-estadisticas.html',
  styleUrl: './municipalidad-vecino-estadisticas.css',
})
export class MunicipalidadVecinoEstadisticas {
  private eventoService: EventoService = inject(EventoService);
  private reciclajeService: ReciclajeService = inject(ReciclajeService);

  cantidadEventos: CantidadEventosLogradosDTO = new CantidadEventosLogradosDTO();
  distrito: string = '';

  columnasTablaResiduos: string[]=["tipo", "cantidad", "peso"];
  dataSourceTablaResiduos:MatTableDataSource<CantidadResiduosDTO>=new MatTableDataSource<CantidadResiduosDTO>();


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
        this.dataSourceTablaResiduos.data=data;
        console.log(this.dataSourceTablaResiduos.data);
      },
      error: error => {
        console.log(error);
      }
    })
  }
}
