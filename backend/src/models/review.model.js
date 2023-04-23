import mongoose from "mongoose";

const reviewSchema = mongoose.Schema({
    patient: {
        type: mongoose.Types.ObjectId,
        ref: "Patient"
    },
    doctor: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor"
    },
    // booking: {
    //     type: mongoose.Types.ObjectId,
    //     ref: "Booking"
    // },
    description: {
        type: String,
    },
    star: {
        type: Number
    },
    liker: [{
        type: mongoose.Types.ObjectId,
        ref: "Patient"
    }]
}, { timestamps: true });
reviewSchema.index({ patient: 1, booking: 1 }, { unique: true });

reviewSchema.virtual('totalLiker').get(function () {
    return this.liker.length;
});

export default mongoose.model("Review", reviewSchema);