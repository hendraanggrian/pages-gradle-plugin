package com.hendraanggrian.pages.heyu

/**
 * Testimony data type for HeyU feature.
 * @param message text in chat bubble, cannot be empty.
 * @param name person who created this testimony, cannot be empty.
 * @param avatar local image of this person, optional.
 * @param from website or company this person is related to, optional.
 * @param position professional level of this person in website or company, optional.
 */
data class HeyUTestimony(
    val message: String,
    val name: String,
    val avatar: String?,
    val from: String?,
    val position: String?
)

/** Receiver for HeyU testimonial block. */
interface HeyUTestimonialScope {
    /**
     * Add testimony.
     * @param message text in chat bubble, cannot be empty.
     * @param name person who created this testimony, cannot be empty.
     * @param avatar local image of this person, optional.
     * @param from website or company this person is related to, optional.
     * @param position professional level of this person in website or company, optional.
     */
    fun testimony(
        message: String,
        name: String,
        avatar: String? = null,
        from: String? = null,
        position: String? = null
    )
}
