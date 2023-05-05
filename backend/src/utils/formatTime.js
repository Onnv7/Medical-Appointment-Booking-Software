import moment from "moment";
export const formatMongooseTime = (type, time) => {

    const formattedDate = moment(time).format(type);

    return formattedDate
}

export const formatFullDate = (type, time) => {
    const dateObj = moment(time, 'hh:mm ddd, DD MMM YYYY');
    return dateObj.format(type);
}