package ifsp.project.welnessmind.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ifsp.project.welnessmind.CONDITIONAL_SCREEN
import ifsp.project.welnessmind.R

const val appName = "Wellness Mind"

@Composable // View
fun FirstScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 48.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_transparent), //
                    contentDescription = "Logo",
                    modifier = Modifier.size(96.dp)
                )
                // Adiciona um espaço entre a imagem e o texto
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Bem-vindo(a) ao",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = appName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontFamily = FontFamily.Serif, // TODO 1. trocar pela Segoe
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF41E8CD)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ajude a sua mente a se sentir mais leve e saudável",
                        fontStyle = Italic,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Central Section
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "O $appName é um aplicativo desenvolvido para ajudar você a gerenciar a sobrecarga de informações do dia a dia " +
                            "e encontrar equilíbrio mental. " +
                            "\nAtravés de ferramentas simples e práticas, nós ajudamos você a criar rotinas saudáveis, " +
                            "reduzir o estresse e promover o bem-estar.",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                val taskDescriptions = listOf(
                    "• Organize suas tarefas de forma clara e simples",
                    "• Reduza distrações digitais com foco no que realmente importa",
                    "• Estabeleça hábitos que melhoram sua saúde mental"
                )
                LazyColumn {
                    items(taskDescriptions) { description ->
                        ListItem(
                            headlineContent = { Text(description) }
                        )
                    }
                }
            }

            // Bottom Section
            Button(
                onClick = { navController.navigate(CONDITIONAL_SCREEN) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E98D7),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text("Explorar".uppercase())
            }
        }
    }
}