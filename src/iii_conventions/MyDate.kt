package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) {
    operator fun rangeTo(other: MyDate): DateRange = DateRange(this, other)

    operator fun compareTo(other: MyDate): Int {
        return if (year != other.year) year - other.year
        else if (month != other.month) month - other.month
        else dayOfMonth - other.dayOfMonth
    }

    operator fun plus(ti: TimeInterval): MyDate {
        return this.addTimeIntervals(ti, 1)
    }

    operator fun plus(rti: RepeatedTimeInterval): MyDate {
        return this.addTimeIntervals(rti.ti, rti.n)
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(n: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, n)
    }
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    operator fun contains(date: MyDate): Boolean {
        return start < date && date <= endInclusive
    }

    override fun iterator(): Iterator<MyDate> {
        return DateIterator(start, endInclusive)
    }

    private class DateIterator(start: MyDate, val endInclusive: MyDate): Iterator<MyDate> {
        private var current: MyDate = start.addTimeIntervals(TimeInterval.DAY, -1)

        override fun hasNext(): Boolean {
            return current.nextDay() <= endInclusive
        }

        override fun next(): MyDate {
            current = current.nextDay()
            return current
        }
    }
}
