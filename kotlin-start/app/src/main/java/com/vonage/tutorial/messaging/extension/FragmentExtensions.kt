package com.vonage.tutorial.messaging.extension

import androidx.fragment.app.FragmentManager

val FragmentManager.currentNavigationFragment
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()
