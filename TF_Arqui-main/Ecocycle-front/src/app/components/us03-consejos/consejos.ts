import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

interface ContenidoConsejo {
  titulo: string;
  parrafos: string[];
  listaConsejos?: string[];
  imagen: string;
  alt: string;
}

@Component({
  selector: 'app-us03-consejos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consejos.html',
  styleUrls: ['./consejos.css']
})
export class Consejos implements OnInit {

  tipo: string = 'organicos';
  imagenesRutas = {
    organicos: '/Consejos-Organico.png',
    plasticos: '/Consejos-Plastico.jpeg',
    metalicos: '/Consejos-Metalico.jpeg',
    electronicos: '/Consejos-Electronico.jpeg'
  };

  contenido: { [key: string]: ContenidoConsejo } = {
    organicos: {
      titulo: '¿Cómo reciclar desde mi casa?',
      parrafos: [
        'Existen varias opciones de cómo deshacerse de residuos orgánicos desde tu propia casa, una de las más populares hoy en día son las composteras.',
        'Estas te pueden servir si eres fan de cuidar plantas o tienes un patio que necesita ayuda para quedarse verde.'
      ],

      imagen: '/Compostera.png',
      alt: 'Reciclaje en casa (Compostaje)'
    },
    plasticos: {
      titulo: 'Reciclaje de plásticos',
      parrafos: [
        'Los plásticos pueden tardar cientos de años en degradarse, por eso es vital reducir su uso y reciclarlos correctamente.',
        'Lava, seca y separa tus botellas, envases y bolsas. Evita los plásticos de un solo uso y prefiere los reutilizables.'
      ],
      imagen: '/Plastico-Consejos.jpg',
      alt: 'Reciclaje de plásticos (Botellas y envases)'
    },
    metalicos: {
      titulo: 'Reciclaje de metales',
      parrafos: [
        'Los metales pueden reciclarse muchas veces sin perder calidad. Clasifica los metales ferrosos y no ferrosos antes de entregarlos.',
        'Limpia las latas o restos metálicos para evitar contaminación cruzada.'
      ],
      imagen: '/Metalico-Consejos.jpeg',
      alt: 'Reciclaje de metales (Latas)'
    },
    electronicos: {
      titulo: 'Residuos electrónicos y baterías',
      parrafos: [
        'Los residuos electrónicos contienen materiales peligrosos y deben tratarse adecuadamente.',
        'Lleva tus baterías, celulares o aparatos viejos a puntos autorizados para su recolección y reciclaje.'
      ],
      imagen: '/Electronico-Consejo.png',
      alt: 'Reciclaje electrónico (Puntos de recolección)'
    }
  };

  consejoActual!: ContenidoConsejo;

  constructor() { }

  ngOnInit(): void {
    this.cambiarTipo(this.tipo);
    console.log('Consejo cargado:', this.consejoActual);
  }


  cambiarTipo(nuevoTipo: string): void {
    this.tipo = nuevoTipo;
    this.consejoActual = this.contenido[nuevoTipo];
  }

  get heroImageSrc(): string {
    return this.imagenesRutas[this.tipo as keyof typeof this.imagenesRutas];
  }
}
