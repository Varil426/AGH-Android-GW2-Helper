package com.gw2helper.entities

import java.text.SimpleDateFormat

data class Account(val name: String, val age: Long, val worldId: String, val guildsIds: List<String>, val gameContentAccess: List<String>, val fractalLevel: Int, val wvwRank: Int)