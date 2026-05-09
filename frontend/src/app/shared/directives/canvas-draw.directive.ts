import { Directive, ElementRef, HostListener, OnInit } from '@angular/core';

@Directive({ selector: 'canvas[coCanvasDraw]', standalone: true })
export class CanvasDrawDirective implements OnInit {
  private ctx!: CanvasRenderingContext2D;
  private drawing = false;

  constructor(private el: ElementRef<HTMLCanvasElement>) {}

  ngOnInit(): void {
    this.ctx = this.el.nativeElement.getContext('2d')!;
    this.ctx.strokeStyle = '#3b82f6';
    this.ctx.lineWidth = 4;
    this.ctx.lineCap = 'round';
  }

  @HostListener('pointerdown', ['$event']) onDown(e: PointerEvent): void {
    this.drawing = true;
    this.ctx.beginPath();
    this.ctx.moveTo(e.offsetX, e.offsetY);
  }

  @HostListener('pointermove', ['$event']) onMove(e: PointerEvent): void {
    if (!this.drawing) return;
    this.ctx.lineTo(e.offsetX, e.offsetY);
    this.ctx.stroke();
  }

  @HostListener('pointerup') @HostListener('pointerleave') onUp(): void {
    this.drawing = false;
  }

  clear(): void {
    const { width, height } = this.el.nativeElement;
    this.ctx.clearRect(0, 0, width, height);
  }
}
