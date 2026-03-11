package com.example.ui.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.screens.home.HomeScreenUiEvents
import com.example.ui.screens.home.viewModel.HomeScreenViewmodel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import productbrowserapp.feature.home.ui.generated.resources.Res
import productbrowserapp.feature.home.ui.generated.resources.profile


@Composable
fun ProductCard(
    name: String?,
    price: Double?,
    thumbnail: String?,
    id: Int?,
    viewModel: HomeScreenViewmodel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
        ,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp).clickable{
                viewModel.onEvents(HomeScreenUiEvents.OnItemClicked(1))
            },
            verticalAlignment = Alignment.CenterVertically
        ) {


            thumbnail?.let {
                KamelImage(
                    resource = asyncPainterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    },
                    onFailure = {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = name ?: "N/A",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "$$price",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}