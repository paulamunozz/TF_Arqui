import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-us19-us20-municipalidad-eventos',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './municipalidad-eventos.html',
  styleUrl: './municipalidad-eventos.css',
})
export class MunicipalidadEventos {
  imgSrc = '/btn-crear-1.png';

  formFiltro: FormGroup;
  private fb = inject(FormBuilder);
  constructor() {
    this.formFiltro = this.fb.group({
      nombre: [''],
      tipo: [''],
      metodo: [''],
      fechaInicio: [''],
      fechaFin: ['']
    });
  }

  eventos = [
    {id:1, nombre:'Evento 1', descripcion:'Nuestra meta con este desafío es lograr para el fin de mes de septiembre recolectar como mínimo 500 kg de todo tipo de plásticos para poder transformarlos en nuevos materiales útiles como bolsas o botellas y reducir la cantidad de desechos que acaban contaminando los mares y las calles de nuestro país.\n' +
        'Asegúrese al momento de reciclar los plásticos que no tengan ningún tipo de desecho orgánico ya que podría causar generación de bacterias y ser riesgoso para nuestro personal que trata con el reciclaje.', tipo:'Papel', metodo:'En casa', fechaInicio:'26/10/2025', fechaFin:'26/10/2025', pesoObjetivo:'500',pesoActual:'125'},
    {id:2, nombre:'Evento 2', descripcion:'Nuestra meta con este desafío es lograr para el fin de mes de septiembre recolectar como mínimo 500 kg de todo tipo de plásticos para poder transformarlos en nuevos materiales útiles como bolsas o botellas y reducir la cantidad de desechos que acaban contaminando los mares y las calles de nuestro país.' +
        'Asegúrese al momento de reciclar los plásticos que no tengan ningún tipo de desecho orgánico ya que podría causar generación de bacterias y ser riesgoso para nuestro personal que trata con el reciclaje.', tipo:'Plástico', metodo:'Centro de reciclaje de la municipalidad', fechaInicio:'26/10/2025', fechaFin:'26/10/2025', pesoObjetivo:'500',pesoActual:'125'},
    {id:3, nombre:'Evento 3', descripcion:'Nuestra meta con este desafío es lograr para el fin de mes de septiembre recolectar como mínimo 500 kg de todo tipo de plásticos para poder transformarlos en nuevos materiales útiles como bolsas o botellas y reducir la cantidad de desechos que acaban contaminando los mares y las calles de nuestro país.\n' +
        'Asegúrese al momento de reciclar los plásticos que no tengan ningún tipo de desecho orgánico ya que podría causar generación de bacterias y ser riesgoso para nuestro personal que trata con el reciclaje.', tipo:'Metal/Lata', metodo:'Camión de basura', fechaInicio:'26/10/2025', fechaFin:'26/10/2025', pesoObjetivo:'500',pesoActual:'125'}
  ]
}
