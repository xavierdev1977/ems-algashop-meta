package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public class Customer {
    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;
    private Address address;

    public static Customer brandNew(FullName fullName, BirthDate birthDate, Email email,
                                    Phone phone, Document document, Boolean promotionNotificationsAllowed,
                                    Address address) {
        return new Customer(new CustomerId(),
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltyPoints.ZERO,
                address);
    }

    public static Customer existing(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone,
                                    Document document, Boolean promotionNotificationsAllowed, Boolean archived,
                                    OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                                    LoyaltyPoints loyaltyPoints, Address address) {
        return new Customer(
                id,
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                archived,
                registeredAt,
                archivedAt,
                loyaltyPoints,
                address
        );

    }

    private Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone,
                    Document document, Boolean promotionNotificationsAllowed, Boolean archived,
                    OffsetDateTime registeredAt, OffsetDateTime archivedAt, LoyaltyPoints loyaltyPoints, Address address) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
        this.setAddress(address);
    }

    public void addLoyaltyPoints(LoyaltyPoints loyaltyPointsAdded) {
        verifyIfChangeable();
        this.setLoyaltyPoints(this.loyaltyPoints().add(loyaltyPointsAdded));
    }

    public void archive() {
        verifyIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationsAllowed(false);
        this.setAddress(this.address().toBuilder()
                .number("Anonymized")
                .complement(null).build());
    }

    public void enablePromotionNotifications() {
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(true);
    }

    public void disablePromotionNotifications() {
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(FullName fullName) {
        verifyIfChangeable();
        this.setFullName(fullName);
    }

    public void changeEmail(Email email) {
        verifyIfChangeable();
        this.setEmail(email);
    }

    public void changePhone(Phone phone) {
        verifyIfChangeable();
        this.setPhone(phone);
    }

    public void changeAddress(Address address) {
        verifyIfChangeable();
        this.setAddress(address);
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    public Address address() {
        return address;
    }

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_NULL);
        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }
        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {
        Objects.requireNonNull(email);
        this.email = email;
    }

    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setDocument(Document document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }

    private void setAddress(Address address) {
        Objects.requireNonNull(address);
        this.address = address;
    }

    private void verifyIfChangeable() {
        if (this.isArchived()) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}