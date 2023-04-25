import mongoose from "mongoose";

const reviewSchema = mongoose.Schema({
    patient: {
        type: mongoose.Types.ObjectId,
        ref: "Patient",
        required: true,
    },
    doctor: {
        type: mongoose.Types.ObjectId,
        ref: "Doctor",
        required: true,
    },
    // booking: {
    //     type: mongoose.Types.ObjectId,
    //     ref: "Booking"
    // },
    description: {
        type: String,
        required: true,
    },
    star: {
        type: Number,
        required: true,
    },
    liker: {
        type: [{
            type: mongoose.Types.ObjectId,
            ref: "Patient",
        }],
        default: []
    }

}, { timestamps: true });
// reviewSchema.index({ patient: 1, booking: 1 }, { unique: true });

reviewSchema.virtual('totalLiker').get(function () {
    return this.liker.length;
});

export default mongoose.model("Review", reviewSchema);