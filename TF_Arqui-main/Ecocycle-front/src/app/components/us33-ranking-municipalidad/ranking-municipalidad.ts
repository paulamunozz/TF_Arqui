import {Component, inject, ViewChild} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {Municipalidad} from '../../model/municipalidad';
import {MunicipalidadService} from '../../services/municipalidad-service';
import {MatPaginator} from '@angular/material/paginator';

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
    MatHeaderCellDef,
    MatPaginator
  ],
  templateUrl: './ranking-municipalidad.html',
  styleUrl: './ranking-municipalidad.css',
})
export class RankingMunicipalidad {
  private municipalidadService: MunicipalidadService = inject(MunicipalidadService);
  columnasRanking: string[]=["puesto", "distrito", "puntaje"];
  dataSourceRanking:MatTableDataSource<Municipalidad>=new MatTableDataSource<Municipalidad>();

  ngOnInit() {
    this.mostrarRanking();
  }
  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngAfterViewInit() {
    this.dataSourceRanking.paginator = this.paginator;
  }

  mostrarRanking() {
    this.municipalidadService.rankingMunicipalidades().subscribe({
      next: (data: Municipalidad[]) => {
        const datosOrdenados = data.sort((a, b) => a.puesto - b.puesto);
        this.dataSourceRanking.data = datosOrdenados;
        console.log('Ranking cargado exitosamente:', datosOrdenados);
      },
      error: (err) => {
        console.error('Error al cargar el ranking. Verifique el token y la conexión al backend.', err);
        this.dataSourceRanking.data = [];
      }
    });
  }
}
