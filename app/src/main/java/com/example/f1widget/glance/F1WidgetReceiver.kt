package com.example.f1widget.glance

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

// This class listens for system events (like "Update Widget")
class F1WidgetReceiver : GlanceAppWidgetReceiver() {

    // We tell it which UI to render (We will build F1Widget next)
    override val glanceAppWidget: GlanceAppWidget = F1Widget()
}