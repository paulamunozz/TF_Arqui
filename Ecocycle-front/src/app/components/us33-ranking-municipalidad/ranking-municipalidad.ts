import { Component } from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {Vecino} from '../../model/vecino';
import {Municipalidad} from '../../model/municipalidad';

@Component({
  selector: 'app-us33-ranking-municipalidad',
  imports: [
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
  templateUrl: './ranking-municipalidad.html',
  styleUrl: './ranking-municipalidad.css',
})
export class RankingMunicipalidad {
  columnasRanking: string[]=["puesto", "distrito", "puntaje"];
  dataSourceRanking:MatTableDataSource<Municipalidad>=new MatTableDataSource<Municipalidad>();

  ngOnInit() {
    this.mostrarRanking();
  }

  mostrarRanking(){

  }
}
