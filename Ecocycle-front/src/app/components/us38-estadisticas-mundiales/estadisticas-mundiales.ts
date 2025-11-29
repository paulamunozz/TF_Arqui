import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as L from 'leaflet';
import {NgForOf, NgStyle} from '@angular/common';
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';

declare const _aqiFeed: any;

interface AqiWidgetConfigItem {
  city: string;
  callback: (aqi: any) => void;
}

export interface AqiLevel {
  aqi: string;
  nivelContaminacion: string;
  implicacionesSalud: string;
  recomendaciones: string;
  color: string;
}

@Component({
  selector: 'app-us38-estadisticas-mundiales',
  imports: [
    NgForOf,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    NgStyle
  ],
  templateUrl: './estadisticas-mundiales.html',
  styleUrl: './estadisticas-mundiales.css',
})
export class EstadisticasMundiales implements AfterViewInit, OnInit {
  private readonly AQI_TOKEN = '2dd49e9dfc4e42ba9f89fdf799d02f98a6b0cf33';

  ngAfterViewInit(): void {
    this.initializeMap();
  }

  private initializeMap(): void {
    const map = L.map('leafletMap').setView([-12.0464, -77.0428], 11);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; [OpenStreetMap](www.openstreetmap.org) contributors'
    }).addTo(map);

    const waqiLayer = L.tileLayer('https://tiles.aqicn.org/tiles/usepa-aqi/{z}/{x}/{y}.png?token=' + this.AQI_TOKEN, {
      attribution: 'Air Quality &copy; [WAQI](https://aqicn.org)',
      opacity: 1
    });

    waqiLayer.addTo(map);
  }


  constructor() { }

  ngOnInit(): void {
    if (typeof _aqiFeed !== 'undefined') {
      this.loadAqiWidgets();
    } else {
      console.error('_aqiFeed script not loaded!');
    }

    this.dataSource.data = this.AQI_DATA;
  }

  loadAqiWidgets(): void {
    const cities = ["lima", "dezhou", "kyoto", "london", "guadalajara", "amsterdam"];

    const aqiWidgetConfig: AqiWidgetConfigItem[] = [];

    cities.forEach((city, index) => {
      aqiWidgetConfig.push({
        city: city,
        callback: (aqi) => this.displayCity(aqi, index)
      });
    });

    _aqiFeed(aqiWidgetConfig);
  }

  displayCity(aqi: any, index: number): void {
    const containerId = `city-aqi-container-${index}`;
    const container = document.getElementById(containerId);

    if (container) {
      const htmlContent = aqi.text(
        "<div style='%style; height: 100px; text-align:center; display: flex; flex-direction: column; justify-content: center'>" +
        "<h3 style='padding: 0; margin: 0;'>%cityname</h3>" +
        "<h3 style='padding: 0; margin: 0;'>%aqiv</h3>" +
        "<small style='padding: 0; margin: 0;'>%date</small></div>");
      container.innerHTML = htmlContent;
    }
  }

  AQI_DATA: AqiLevel[] = [
    {  aqi: '0 - 50',
      nivelContaminacion: 'Bueno',
      implicacionesSalud: 'La calidad del aire se considera satisfactoria y la contaminación del aire supone poco o ningún riesgo',
      recomendaciones: 'Ninguna',
      color: '#009866' },
    { aqi: '51 - 100',
      nivelContaminacion: 'Moderado',
      implicacionesSalud: 'La calidad es aceptable. Puede haber un riesgo moderado para un pequeño número de personas muy sensibles',
      recomendaciones: 'Niños y adultos activos, y personas con enfermedades respiratorias como asma, deben limitar esfuerzos prolongados al aire libre',
      color: '#fddd33' },
    { aqi: '101 - 150',
      nivelContaminacion: 'No saludable para grupos sensibles',
      implicacionesSalud: 'Grupos sensibles pueden experimentar efectos en la salud. El público general probablemente no',
      recomendaciones: 'Niños y adultos activos, y personas con enfermedades respiratorias como asma, deben limitar esfuerzos prolongados al aire libre',
      color: '#fd9833' },
    { aqi: '151 - 200',
      nivelContaminacion: 'No saludable',
      implicacionesSalud: 'Todos pueden experimentar efectos en la salud. Grupos sensibles pueden tener efectos más graves',
      recomendaciones: 'Todos, en especial niños y adultos vulnerables, y personas con enfermedades respiratorias como asma, deben evitar exposición prolongada al aire libre',
      color: '#cb0033' },
    { aqi: '201 - 300',
      nivelContaminacion: 'Muy no saludable',
      implicacionesSalud: 'Advertencias de salud por condiciones de emergencia. Toda la población es afectada',
      recomendaciones: 'Todos, en especial niños y adultos vulnerables, y personas con enfermedades respiratorias como asma, deben evitar toda exposición al aire libre',
      color: '#660098' },
    { aqi: '300+',
      nivelContaminacion: 'Peligroso',
      implicacionesSalud: 'Alerta de salud: todos pueden experimentar efectos graves',
      recomendaciones: 'Todos deben evitar cualquier exposición al aire libre',
      color: '#7d0023' }
  ];

  displayedColumns: string[] = ['aqi', 'nivelContaminacion', 'implicacionesSalud', 'recomendaciones'];
  dataSource = new MatTableDataSource<AqiLevel>();
}
