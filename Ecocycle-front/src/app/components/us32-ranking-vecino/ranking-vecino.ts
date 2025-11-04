import { Component } from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {CantidadResiduosDTO} from '../../model/reportes/cantidad-residuos-dto';
import {Vecino} from '../../model/vecino';
import {FormsModule} from '@angular/forms';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-us32-ranking-vecino',
  imports: [
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    FormsModule,
    MatInput,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef
  ],
  templateUrl: './ranking-vecino.html',
  styleUrl: './ranking-vecino.css',
})
export class RankingVecino {
  columnasRanking: string[]=["puesto", "nombre", "puntaje", "distrito"];
  dataSourceRanking:MatTableDataSource<Vecino>=new MatTableDataSource<Vecino>();

  distrito: string = '';
  genero: string = '';
  edadMin: number;
  edadMax: number;

  ngOnInit() {
    this.filtrarRanking();
  }

  filtrarRanking(){

  }
}
