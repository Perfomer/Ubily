package com.vmedia.core.common.util

import java.math.BigDecimal

operator fun BigDecimal.times(other: Int) = multiply(BigDecimal(other))